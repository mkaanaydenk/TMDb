package com.mehmetkaanaydenk.tmdb.model


import com.google.gson.annotations.SerializedName

data class MovieGenres(
    @SerializedName("genres")
    val movieGenres: List<MovieGenre>
)