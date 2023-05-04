package com.sergei.apprecipes.searchonline

import android.app.DownloadManager.Query
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.apprecipes.network.OnlineRecipeBasic
import com.sergei.apprecipes.network.SpoonacularApiService
import kotlinx.coroutines.launch

const val GIFS_ON_FIRST_LOAD = 10

class SearchOnlineViewModel : ViewModel() {
    private val TAG = "SearchOnline ViewModel"

    private val _recipes = MutableLiveData(mutableListOf<OnlineRecipeBasic>())
    val recipes: LiveData<MutableList<OnlineRecipeBasic>> = _recipes

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

    init {
        Log.d(TAG, "ViewModel created")
        searchRecipes("Oatmeal")
    }
}