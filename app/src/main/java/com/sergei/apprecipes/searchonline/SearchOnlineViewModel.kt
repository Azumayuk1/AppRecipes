package com.sergei.apprecipes.searchonline

import android.util.Log
import androidx.lifecycle.*
import com.sergei.apprecipes.database.RecipeLocal
import com.sergei.apprecipes.database.RecipeLocalDao
import com.sergei.apprecipes.network.*
import com.sergei.apprecipes.prepareRecipeOnlineIngredients
import kotlinx.coroutines.launch

const val GIFS_ON_FIRST_LOAD = 15

enum class ApiStatus {
    WAITING, OK, NO_RESPONSE, CONNECTION_ERROR
}

class SearchOnlineViewModelFactory(private val recipeLocalDao: RecipeLocalDao) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchOnlineViewModel::class.java)) {
            return SearchOnlineViewModel(recipeLocalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SearchOnlineViewModel(private val recipeLocalDao: RecipeLocalDao) : ViewModel() {
    private val TAG = "SearchOnline ViewModel"

    // Used to notify the user if there is a connection error
    private val _apiStatus = MutableLiveData(ApiStatus.WAITING)
    val apiStatus: LiveData<ApiStatus> = _apiStatus

    private val _recipes = MutableLiveData(mutableListOf<OnlineRecipeBasic>())
    val recipes: LiveData<MutableList<OnlineRecipeBasic>> = _recipes

    private val _currentRecipe = MutableLiveData<SpoonacularRecipeResponse>()
    val currentRecipe: LiveData<SpoonacularRecipeResponse> = _currentRecipe

    fun searchRecipes(searchQuery: String, isVegan: Boolean = false) {
        clearRecipes()

        viewModelScope.launch {
            try {
                val response = when(isVegan) {
                    false -> SpoonacularApiService.retrofitApiService
                        .getSearchedRecipes(searchQuery, GIFS_ON_FIRST_LOAD)
                    true -> SpoonacularApiService.retrofitApiService
                        .getSearchedRecipesOnlyVegan(searchQuery, GIFS_ON_FIRST_LOAD)
                }

                // Loading recipes
                _recipes.postValue(response.results.toMutableList())

                Log.d(TAG, "API response: loaded, size: ${_recipes.value?.size ?: 0}")
                _apiStatus.value = ApiStatus.OK
            } catch (e: Exception) {
                Log.e(TAG, "${e.message}")
                _apiStatus.value = ApiStatus.NO_RESPONSE
            }
        }
    }

    private fun clearRecipes() {
        _recipes.value?.clear()
    }

    fun retrieveRecipeById(id: Int) {
        viewModelScope.launch {
            try {
                _currentRecipe.value = SpoonacularApiService.retrofitApiService.getRecipeById(id)
                Log.d(TAG, "Recipe with id ${id} retrieved, title: ${_currentRecipe.value?.title}")
                _apiStatus.value = ApiStatus.OK
            } catch (e: Exception) {
                Log.e(TAG, "${e.message}")
                _apiStatus.value = ApiStatus.NO_RESPONSE
            }
        }
    }

    /**
     * Saves the recipe to user's device.
     */
    fun downloadRecipe() {
        if (_currentRecipe.value != null) {
            viewModelScope.launch {
                recipeLocalDao.insertNew(
                    RecipeLocal(
                        imagePath = _currentRecipe.value?.imageUrl,
                        name = _currentRecipe.value?.title ?: "No title",
                        filter = _currentRecipe.value?.dishCategories?.get(0) ?: "No category",
                        ingredients = prepareRecipeOnlineIngredients(_currentRecipe.value?.ingredients),
                        instructions = prepareSpoonacularInstructions(_currentRecipe.value?.summary)
                    )
                )
            }
        }
    }

    init {
        Log.d(TAG, "ViewModel created")
    }
}