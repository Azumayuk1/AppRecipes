package com.sergei.apprecipes.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeLocalDao {
    @Query("SELECT * FROM RecipeLocal")
    fun getAll(): Flow<List<RecipeLocal>>


    @Query("SELECT * FROM RecipeLocal WHERE filter = :filter OR name = :name")
    fun getAllByNameAndFilter(filter: String, name: String): Flow<List<RecipeLocal>>

    @Query("SELECT * FROM RecipeLocal WHERE id = :id")
    fun getRecipeById(id: Int): Flow<RecipeLocal>

    /**
     * Insert new recipe to database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNew(recipeLocal: RecipeLocal)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateRecipe(recipe: RecipeLocal)

    @Delete
    suspend fun deleteRecipe(recipeLocal: RecipeLocal)

}