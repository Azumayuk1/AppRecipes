package com.sergei.apprecipes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeLocalDao {
    @Query("SELECT * FROM RecipeLocal")
    fun getAll(): List<RecipeLocal>

    @Query("SELECT * FROM RecipeLocal WHERE filter = \":filter\"")
    fun getAllByFilter(filter: String): List<RecipeLocal>

    @Insert
    fun insertNew(recipeLocal: RecipeLocal)
}