package com.sergei.apprecipes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
    Recipe that is stored in local database.
 */
@Entity
data class RecipeLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image_path") val imagePath: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "filter") val filter: String?,
    @ColumnInfo(name = "ingredients") val ingredients: String?,
    @ColumnInfo(name = "instructions") val instructions: String?,
)
