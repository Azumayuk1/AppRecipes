package com.sergei.apprecipes.searchlocal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchLocalViewModel: ViewModel() {

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