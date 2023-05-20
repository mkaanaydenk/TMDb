package com.mehmetkaanaydenk.tmdb.model


import com.google.gson.annotations.SerializedName

data class Tv(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultTvs: List<ResultTv>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)