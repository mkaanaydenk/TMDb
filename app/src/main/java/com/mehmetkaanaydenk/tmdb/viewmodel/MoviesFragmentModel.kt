package com.mehmetkaanaydenk.tmdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.tmdb.model.Movie
import com.mehmetkaanaydenk.tmdb.model.MovieGenre
import com.mehmetkaanaydenk.tmdb.model.MovieGenres
import com.mehmetkaanaydenk.tmdb.model.ResultMovie
import com.mehmetkaanaydenk.tmdb.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MoviesFragmentModel: ViewModel() {

    private val disposable = CompositeDisposable()
    private val movieAPIService = MovieAPIService()

    val movies = MutableLiveData<List<ResultMovie>>()

    val movieGenres = MutableLiveData<List<MovieGenre>>()

    val selectedGenreId = MutableLiveData<String>("28")

    val selectedSortBy = MutableLiveData<String>()

    val includeAdult = MutableLiveData<Boolean>(false)

    val progressBar = MutableLiveData<Boolean>()

    val movieError = MutableLiveData<Boolean>()

    val recyclerView = MutableLiveData<Boolean>()


    fun setGenre(genre: String){

        selectedGenreId.value = genre

    }

    fun setSort(sortBy: String){

        selectedSortBy.value = sortBy

    }

    fun setAdult(adult: Boolean){

        includeAdult.value = adult

    }

    fun getMovies(genre: String, sort: String, includeAdult: Boolean){

        progressBar.value = true

        disposable.add(
            movieAPIService.getMovieFragmentMovies(genre,sort,includeAdult)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {
                        progressBar.value = false
                        movieError.value = false
                        recyclerView.value = true
                        movies.value = t.resultMovies
                    }

                    override fun onError(e: Throwable) {
                        progressBar.value = false
                        recyclerView.value = false
                        movieError.value = true
                    }


                })
        )

    }

    fun getGenres(){

        disposable.add(
            movieAPIService.getMovieGenres()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieGenres>(){
                    override fun onSuccess(t: MovieGenres) {
                        movieGenres.value = t.movieGenres
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                    }


                })
        )

    }

}