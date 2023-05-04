package com.sergei.apprecipes.network

import com.squareup.moshi.Json

data class SpoonacularRecipeResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "image") val imageUrl: String,
    @Json(name = "extendedIngredients") val ingredients: List<Ingredient>
)

data class Ingredient(
    @Json(name = "original") val info: String,
)
