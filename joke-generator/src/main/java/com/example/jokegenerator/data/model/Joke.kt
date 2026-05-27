package com.example.jokegenerator.data.model

import com.google.gson.annotations.SerializedName

// Response from JokeAPI (Single joke)
data class JokeResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("category")
    val category: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("joke")
    val joke: String? = null,
    @SerializedName("setup")
    val setup: String? = null,
    @SerializedName("delivery")
    val delivery: String? = null,
    @SerializedName("flags")
    val flags: Flags,
    @SerializedName("id")
    val id: Int,
    @SerializedName("safe")
    val safe: Boolean,
    @SerializedName("lang")
    val lang: String
)

data class Flags(
    @SerializedName("nsfw")
    val nsfw: Boolean,
    @SerializedName("religious")
    val religious: Boolean,
    @SerializedName("political")
    val political: Boolean,
    @SerializedName("racist")
    val racist: Boolean,
    @SerializedName("sexist")
    val sexist: Boolean,
    @SerializedName("explicit")
    val explicit: Boolean
)

// Domain model for UI
data class Joke(
    val id: Int,
    val category: String,
    val content: String,
    val type: String,
    val isSafe: Boolean
) {
    companion object {
        fun fromResponse(response: JokeResponse): Joke {
            val content = if (response.type == "single") {
                response.joke ?: ""
            } else {
                "${response.setup}\n\n${response.delivery}"
            }
            return Joke(
                id = response.id,
                category = response.category,
                content = content,
                type = response.type,
                isSafe = response.safe
            )
        }
    }
}
