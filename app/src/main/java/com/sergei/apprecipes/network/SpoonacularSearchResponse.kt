package com.sergei.apprecipes.network

import com.squareup.moshi.Json

/**
 * Response from API search, contains a list of recipes.
 */
data class SpoonacularSearchResponse(
    @Json(name="results") val results: List<OnlineRecipeBasic>,
    @Json(name ="totalResults") val totalResults: Int
)

/**
 * Basic information about one single recipe received from API - ID, name, image URL.
 * Normally only used to portray an item in recipes list.
 */
data class OnlineRecipeBasic(
    @Json(name="id") val id: Int,
    @Json(name="title") val title: String,
    @Json(name="image") val imageSrc: String
)