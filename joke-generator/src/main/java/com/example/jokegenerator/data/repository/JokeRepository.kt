package com.example.jokegenerator.data.repository

import com.example.jokegenerator.data.api.JokeApiService
import com.example.jokegenerator.data.model.Joke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JokeRepository(private val apiService: JokeApiService) {
    suspend fun getRandomJoke(safeMode: Boolean = false): Result<Joke> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getRandomJoke(safeMode = safeMode)
            if (response.error) {
                Result.failure(Exception("API returned an error"))
            } else {
                Result.success(Joke.fromResponse(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRandomJokeByCategory(category: String, safeMode: Boolean = false): Result<Joke> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getRandomJokeByCategory(category, safeMode = safeMode)
                if (response.error) {
                    Result.failure(Exception("API returned an error"))
                } else {
                    Result.success(Joke.fromResponse(response))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getAvailableCategories(): Result<List<String>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAvailableCategories()
            Result.success(response.categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
