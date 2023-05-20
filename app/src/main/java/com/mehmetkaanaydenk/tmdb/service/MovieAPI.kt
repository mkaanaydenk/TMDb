package com.mehmetkaanaydenk.tmdb.service

import com.mehmetkaanaydenk.tmdb.model.Movie
import com.mehmetkaanaydenk.tmdb.model.MovieGenres
import com.mehmetkaanaydenk.tmdb.model.Tv
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    //base url https://api.themoviedb.org/3/
    @GET("discover/movie?api_key=dc1de248e4902fda84ad5dad5068ca5b&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=false&page=1&primary_release_year=2022")
    fun getPopular22(@Query("language") language: String): Single<Movie>

    @GET("discover/movie?api_key=dc1de248e4902fda84ad5dad5068ca5b&language=en-US&sort_by=release_date.desc&include_adult=false&include_video=false&page=3")
    fun getComingSoon(@Query("language") language: String): Single<Movie>

    @GET("discover/movie?api_key=dc1de248e4902fda84ad5dad5068ca5b&with_genres=27&sort_by=popularity.desc&page=1")
    fun getHorror(@Query("language") language: String): Single<Movie>

    @GET("discover/movie?api_key=dc1de248e4902fda84ad5dad5068ca5b&with_genres=16&sort_by=popularity.desc&page=1")
    fun getAnimation(@Query("language") language: String): Single<Movie>

    @GET("search/movie?api_key=dc1de248e4902fda84ad5dad5068ca5b&page=1")
    fun getByQueryMovie(
        @Query("language") language: String,
        @Query("query") query: String
    ): Single<Movie>

    @GET("search/tv?api_key=dc1de248e4902fda84ad5dad5068ca5b&page=1")
    fun getByQueryTv(
        @Query("language") language: String,
        @Query("query") query: String
    ): Single<Tv>

    @GET("https://api.themoviedb.org/3/genre/movie/list?api_key=dc1de248e4902fda84ad5dad5068ca5b")
    fun getMovieGenres(@Query("language") language: String): Single<MovieGenres>

    @GET("discover/movie?api_key=dc1de248e4902fda84ad5dad5068ca5b&include_video=false&page=1")
    fun getMovieFragmentMovies(
        @Query("language") language: String,
        @Query("with_genres") withGenres: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: Boolean
    ): Single<Movie>

}