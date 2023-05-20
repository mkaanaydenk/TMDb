package com.mehmetkaanaydenk.tmdb.service

import com.mehmetkaanaydenk.tmdb.model.Movie
import com.mehmetkaanaydenk.tmdb.model.MovieGenres
import com.mehmetkaanaydenk.tmdb.model.Tv
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class MovieAPIService {

    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieAPI::class.java)


    fun getPopular22(): Single<Movie> {
        return movieApi.getPopular22(Locale.getDefault().language)

    }

    fun getComingSoon(): Single<Movie> {
        return movieApi.getComingSoon(Locale.getDefault().language)
    }

    fun getHorror(): Single<Movie> {
        return movieApi.getHorror(Locale.getDefault().language)
    }

    fun getAnimation(): Single<Movie> {
        return movieApi.getAnimation(Locale.getDefault().language)
    }

    fun getMovieByQuery(query: String): Single<Movie> {

        return movieApi.getByQueryMovie(Locale.getDefault().language, query)

    }
    fun getTvByQuery(query: String): Single<Tv> {

        return movieApi.getByQueryTv(Locale.getDefault().language, query)

    }

    fun getMovieGenres(): Single<MovieGenres>{

        return movieApi.getMovieGenres(Locale.getDefault().language)

    }

    fun getMovieFragmentMovies(withGenre: String, sortBy: String, includeAdult: Boolean):Single<Movie>{

        return movieApi.getMovieFragmentMovies(Locale.getDefault().language,withGenre,sortBy,includeAdult)

    }

}