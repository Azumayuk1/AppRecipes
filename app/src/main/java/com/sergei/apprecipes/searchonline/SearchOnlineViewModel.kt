package com.sergei.apprecipes.searchonline

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.apprecipes.network.OnlineRecipeBasic
import com.sergei.apprecipes.network.SpoonacularApiService
import com.sergei.apprecipes.network.SpoonacularRecipeResponse
import kotlinx.coroutines.launch

const val GIFS_ON_FIRST_LOAD = 10

class SearchOnlineViewModel : ViewModel() {
    private val TAG = "SearchOnline ViewModel"

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
            } catch (e: Exception) {
                Log.e(TAG, "${e.message}")
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
            } catch (e: Exception) {
                Log.e(TAG, "${e.message}")
            }
        }
    }

    init {
        Log.d(TAG, "ViewModel created")
        searchRecipes("Oatmeal")
    }
}