package com.example.jokegenerator.data.api

import com.example.jokegenerator.data.model.JokeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApiService {
    @GET("jokes/random")
    suspend fun getRandomJoke(
        @Query("format") format: String = "json",
        @Query("safe-mode") safeMode: Boolean = false,
        @Query("type") type: String? = null
    ): JokeResponse

    @GET("jokes/random")
    suspend fun getRandomJokeByCategory(
        @Query("category") category: String,
        @Query("format") format: String = "json",
        @Query("safe-mode") safeMode: Boolean = false
    ): JokeResponse

    @GET("info")
    suspend fun getAvailableCategories(): CategoriesResponse
}

data class CategoriesResponse(
    val categories: List<String>,
    val categoryAliases: Map<String, List<String>>,
    val types: List<String>,
    val flags: List<String>,
    val jokeCount: Int,
    val safeJokeCount: Int
)
