package com.mehmetkaanaydenk.tmdb.model


import com.google.gson.annotations.SerializedName

data class Credit(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)