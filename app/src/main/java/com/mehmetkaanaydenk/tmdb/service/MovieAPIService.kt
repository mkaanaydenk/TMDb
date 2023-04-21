package com.mehmetkaanaydenk.tmdb.service

import com.mehmetkaanaydenk.tmdb.model.Movie
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {

    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieAPI::class.java)


    fun getPopular22(): Single<Movie> {
        return movieApi.getPopular22()

    }

    fun getComingSoon(): Single<Movie> {
        return movieApi.getComingSoon()
    }

    fun getHorror(): Single<Movie> {
        return movieApi.getHorror()
    }

    fun getAnimation(): Single<Movie> {
        return movieApi.getAnimation()
    }

}