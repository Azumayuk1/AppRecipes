package com.sergei.apprecipes.searchlocal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergei.apprecipes.database.RecipeLocalDao

class SearchLocalViewModelFactory(private val recipeLocalDao: RecipeLocalDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchLocalViewModel::class.java)) {
            return SearchLocalViewModel(recipeLocalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SearchLocalViewModel(private val recipeLocalDao: RecipeLocalDao): ViewModel() {
    private val _recipes = MutableLiveData<MutableList<String>>()
    val recipes: LiveData<MutableList<String>> = _recipes

    fun loadRecipes() {}

    fun loadSearchedRecipes() {}

    fun clearRecipesList() {
        _recipes.value?.clear()
    }

    init {
        loadRecipes()
    }
}