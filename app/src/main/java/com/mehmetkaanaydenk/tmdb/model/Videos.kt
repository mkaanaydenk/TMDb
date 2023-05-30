package com.mehmetkaanaydenk.tmdb.model


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val videos: List<Video>
)