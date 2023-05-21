package com.mehmetkaanaydenk.tmdb.model


import com.google.gson.annotations.SerializedName

data class TvGenre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)