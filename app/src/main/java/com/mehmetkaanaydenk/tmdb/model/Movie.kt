package com.mehmetkaanaydenk.tmdb.model


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultMovies: List<ResultMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,

    var titleName: String,
    var id : Long
)