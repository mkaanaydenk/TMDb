package com.mehmetkaanaydenk.tmdb.model


import com.google.gson.annotations.SerializedName

data class TvGenres(
    @SerializedName("genres")
    val tvGenres: List<TvGenre>
)