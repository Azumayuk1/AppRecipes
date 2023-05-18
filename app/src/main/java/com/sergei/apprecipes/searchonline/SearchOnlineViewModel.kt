package com.sergei.apprecipes.searchonline

import android.util.Log
import androidx.lifecycle.*
import com.sergei.apprecipes.database.RecipeLocal
import com.sergei.apprecipes.database.RecipeLocalDao
import com.sergei.apprecipes.network.OnlineRecipeBasic
import com.sergei.apprecipes.network.SpoonacularApiService
import com.sergei.apprecipes.network.SpoonacularRecipeResponse
import com.sergei.apprecipes.network.prepareSpoonacularInstructions
import com.sergei.apprecipes.prepareRecipeOnlineIngredients
import kotlinx.coroutines.launch

const val GIFS_ON_FIRST_LOAD = 10

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

    private val _apiStatus = MutableLiveData(ApiStatus.WAITING)
    val apiStatus: LiveData<ApiStatus> = _apiStatus

    private val _recipes = MutableLiveData(mutableListOf<OnlineRecipeBasic>())
    val recipes: LiveData<MutableList<OnlineRecipeBasic>> = _recipes

    private val _currentRecipe = MutableLiveData<SpoonacularRecipeResponse>()
    val currentRecipe: LiveData<SpoonacularRecipeResponse> = _currentRecipe

    fun searchRecipes(searchQuery: String) {
        clearRecipes()

        viewModelScope.launch {
            try {
                val response = SpoonacularApiService.retrofitApiService
                    .getSearchedRecipes(searchQuery, GIFS_ON_FIRST_LOAD)

                _recipes.value?.addAll(response.results)

                Log.d(TAG, "API response: loaded, size: ${_recipes.value?.size ?: 0}")
                _apiStatus.value = ApiStatus.OK
            } catch (e: Exception) {
                Log.e(TAG, "${e.message}")
                _apiStatus.value = ApiStatus.NO_RESPONSE
            }
        }
    }

    fun clearRecipes() {
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

    fun downloadRecipe() {
        if (_currentRecipe.value != null) {
            viewModelScope.launch {
                recipeLocalDao.insertNew(
                    RecipeLocal(
                        imagePath = _currentRecipe.value?.imageUrl,
                        name = _currentRecipe.value?.title ?: "No title",
                        filter = _currentRecipe.value?.dishCategories?.get(0) ?: "null",
                        ingredients = prepareRecipeOnlineIngredients(_currentRecipe.value?.ingredients),
                        instructions = prepareSpoonacularInstructions(_currentRecipe.value?.summary)
                    )
                )
            }
        }
    }

    init {
        Log.d(TAG, "ViewModel created")

        // Debug
        //searchRecipes("Oatmeal")
    }
}