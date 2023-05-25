package com.sergei.apprecipes.network

import com.squareup.moshi.Json

/**
 * Single recipe received from API.
 * Used to load detailed information about recipe.
 */
data class SpoonacularRecipeResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "image") val imageUrl: String,
    @Json(name = "dishTypes") val dishCategories: List<String>,
    @Json(name = "extendedIngredients") val ingredients: List<Ingredient>,
    @Json(name = "instructions") val summary: String
)

/**
 * Single ingredient used in recipe.
 */
data class Ingredient(
    @Json(name = "original") val info: String,
)
