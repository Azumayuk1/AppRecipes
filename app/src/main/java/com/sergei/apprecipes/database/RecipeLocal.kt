package com.sergei.apprecipes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeLocal(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "image_src") val image: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "filter") val filter: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "instructions") val instructions: String
)
