package com.sergei.apprecipes.searchlocal

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
    //private val _recipes = MutableLiveData<MutableList<RecipeLocal>>()
    val recipes: LiveData<List<RecipeLocal>> = loadRecipes()

    fun loadRecipes(): LiveData<List<RecipeLocal>> {
        return recipeLocalDao.getAll().asLiveData()
    }

    fun loadSearchedRecipes() {}

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

    fun getNewRecipeEntry(
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
}