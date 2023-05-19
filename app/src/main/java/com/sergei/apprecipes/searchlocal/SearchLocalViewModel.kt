package com.sergei.apprecipes.searchlocal

import android.util.Log
import androidx.lifecycle.*
import com.sergei.apprecipes.database.RecipeLocal
import com.sergei.apprecipes.database.RecipeLocalDao
import kotlinx.coroutines.launch

class SearchLocalViewModelFactory(private val recipeLocalDao: RecipeLocalDao) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchLocalViewModel::class.java)) {
            return SearchLocalViewModel(recipeLocalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SearchLocalViewModel(private val recipeLocalDao: RecipeLocalDao) : ViewModel() {
    private val TAG = "ViewModelLocalRecipes"

    var recipes: LiveData<List<RecipeLocal>> = getAllRecipes()


    private fun getAllRecipes(): LiveData<List<RecipeLocal>> {
        return recipeLocalDao.getAll().asLiveData()
    }

    private fun getSearchedRecipes(query: String): LiveData<List<RecipeLocal>> {
        return recipeLocalDao.getAllByNameAndFilter(query, query).asLiveData()
    }

    fun loadAllRecipes() {
        recipes = getAllRecipes()
    }

    fun loadSearchedRecipes(query: String) {
        recipes = getSearchedRecipes(query)
    }

    fun retrieveRecipeById(id: Int): LiveData<RecipeLocal> {
        return recipeLocalDao.getRecipeById(id).asLiveData()
    }

    // Adding new recipe to database
    fun isNewRecipeCorrect(
        name: String?,
        instructions: String?
    ): Boolean {
        if (name.isNullOrBlank() || instructions.isNullOrBlank()) {
            return false
        }
        return true
    }

    private fun getNewRecipeEntry(
        imagePath: String?,
        name: String,
        filter: String?,
        ingredients: String?,
        instructions: String
    ): RecipeLocal {
        return RecipeLocal(
            imagePath = imagePath,
            name = name,
            filter = filter,
            ingredients = ingredients,
            instructions = instructions
        )
    }

    private fun insertNewRecipe(recipe: RecipeLocal) {
        viewModelScope.launch {
            recipeLocalDao.insertNew(recipe)
        }
    }

    fun addNewRecipe(
        imagePath: String?,
        name: String,
        filter: String?,
        ingredients: String?,
        instructions: String
    ) {
        val newRecipeLocal = getNewRecipeEntry(imagePath, name, filter, ingredients, instructions)
        insertNewRecipe(newRecipeLocal)
    }

    // Updating the existing recipe
    private fun getUpdatedRecipeEntry(
        id: Int,
        imagePath: String?,
        name: String,
        filter: String?,
        ingredients: String?,
        instructions: String
    ): RecipeLocal {
        return RecipeLocal(
            id = id,
            imagePath = imagePath,
            name = name,
            filter = filter,
            ingredients = ingredients,
            instructions = instructions
        )
    }

    fun editRecipe(
        id: Int,
        imagePath: String?,
        name: String,
        filter: String?,
        ingredients: String?,
        instructions: String
    ) {
        val updatedRecipe =
            getUpdatedRecipeEntry(id, imagePath, name, filter, ingredients, instructions)
        updateRecipe(updatedRecipe)
    }

    private fun updateRecipe(recipe: RecipeLocal) {
        viewModelScope.launch {
            recipeLocalDao.updateRecipe(recipe)
        }
    }

    // Deleting recipe
    fun deleteRecipe(recipe: RecipeLocal?) {
        if (recipe != null) {
            viewModelScope.launch { recipeLocalDao.deleteRecipe(recipe) }
        } else {
            Log.e(TAG, "Recipe is null, can't be deleted")
        }
    }
}