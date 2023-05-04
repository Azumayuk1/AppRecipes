package com.sergei.apprecipes.network

import com.squareup.moshi.Json

data class SpoonacularSearchResponse(
    @Json(name="results") val results: List<OnlineRecipeBasic>,
    @Json(name ="totalResults") val totalResults: Int
)

data class OnlineRecipeBasic(
    @Json(name="id") val id: Int,
    @Json(name="title") val title: String,
    @Json(name="image") val imageSrc: String
)