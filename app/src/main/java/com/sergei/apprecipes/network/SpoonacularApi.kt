package com.sergei.apprecipes.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.spoonacular.com/"

// You need to put your own Spoonacular API key here
private const val API_KEY = ApiKeys.SPOONACULAR_API_KEY

// Setting up Retrofit and Moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface SpoonacularApi {
    @GET("recipes/complexSearch?apiKey=${API_KEY}")
    suspend fun getSearchedRecipes(
        @Query("query") searchQuery: String,
        @Query("number") numberOfRecipes: Int
    ): SpoonacularSearchResponse

    @GET("recipes/complexSearch?apiKey=${API_KEY}&diet=vegan|vegetarian")
    suspend fun getSearchedRecipesOnlyVegan(
        @Query("query") searchQuery: String,
        @Query("number") numberOfRecipes: Int
    ): SpoonacularSearchResponse

    @GET("recipes/{id}/information?apiKey=${API_KEY}")
    suspend fun getRecipeById(
        @Path("id") recipeId: Int
    ) : SpoonacularRecipeResponse
}

/**
 * Prepares instructions from API by removing HTML tags
 */
fun prepareSpoonacularInstructions(instructionsInput: String?): String {
    if (instructionsInput.isNullOrBlank()) {
        return ""
    } else {
        var instructions = instructionsInput

        // Checking if recipe contains HTML tags
        if (instructions.contains("</")) {
            instructions = instructions.replace("<ol>".toRegex(), "")
            instructions = instructions.replace("</ol>".toRegex(), "")
            instructions = instructions.replace("<li>".toRegex(), "")
            instructions = instructions.replace("</li>".toRegex(), "\n")
        }

        return instructions
    }
}

// Creating Spoonacular API singleton
object SpoonacularApiService {
    val retrofitApiService : SpoonacularApi by lazy {
        retrofit.create(SpoonacularApi::class.java)
    }
}