package com.mehmetkaanaydenk.tmdb.service

import com.mehmetkaanaydenk.tmdb.model.Movie
import io.reactivex.Single
import retrofit2.http.GET

interface MovieAPI {

    //base url https://api.themoviedb.org/3/
    @GET("discover/movie?api_key=API_KEY&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=false&page=1&primary_release_year=2022")
    fun getPopular22(): Single<Movie>

    @GET("discover/movie?api_key=API_KEY&language=en-US&sort_by=release_date.desc&include_adult=false&include_video=false&page=3")
    fun getComingSoon(): Single<Movie>

    @GET("discover/movie?api_key=API_KEY&with_genres=27&sort_by=popularity.desc&page=1")
    fun getHorror(): Single<Movie>

    @GET("discover/movie?api_key=API_KEY&with_genres=16&sort_by=popularity.desc&page=1")
    fun getAnimation(): Single<Movie>


}