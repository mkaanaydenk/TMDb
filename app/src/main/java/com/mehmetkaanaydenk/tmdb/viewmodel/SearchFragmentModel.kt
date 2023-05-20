package com.mehmetkaanaydenk.tmdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.tmdb.model.Movie
import com.mehmetkaanaydenk.tmdb.model.ResultMovie
import com.mehmetkaanaydenk.tmdb.model.ResultTv
import com.mehmetkaanaydenk.tmdb.model.Tv
import com.mehmetkaanaydenk.tmdb.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchFragmentModel: ViewModel() {

    private val movieAPIService = MovieAPIService()
    private val disposable = CompositeDisposable()

    val query = MutableLiveData<String>()
    val movies = MutableLiveData<List<ResultMovie>>()
    val tvSeries = MutableLiveData<List<ResultTv>>()

    val movieLoading = MutableLiveData<Boolean>()
    val tvLoading = MutableLiveData<Boolean>()

    val movieNotFound = MutableLiveData<Boolean>()
    val tvNotFound = MutableLiveData<Boolean>()

    val movieError = MutableLiveData<Boolean>()
    val tvError = MutableLiveData<Boolean>()

    val tabLayout = MutableLiveData<Boolean>()

    fun setQuery(searchQuery: String){

        query.value = searchQuery

    }

    fun getMovies(mQuery: String){

        tabLayout.value = true

        movieLoading.value = true

        disposable.add(
            movieAPIService.getMovieByQuery(mQuery)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {
                        movieLoading.value = false
                        movieError.value = false
                        if (t.totalResults==0){
                            movieNotFound.value = true
                        }else{
                            movies.value = t.resultMovies
                            movieNotFound.value = false

                        }
                    }

                    override fun onError(e: Throwable) {
                        movieLoading.value = false
                        movieNotFound.value = false
                        movieError.value = true
                    }

                })
        )

    }

    fun getTv(tQuery: String){

        tabLayout.value = true

        tvLoading.value = true
        disposable.add(
            movieAPIService.getTvByQuery(tQuery)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Tv>(){
                    override fun onSuccess(t: Tv) {
                        tvLoading.value = false
                        tvError.value = false
                        if (t.totalResults==0){
                            tvNotFound.value = true
                        }else{
                            tvNotFound.value = false
                            tvSeries.value = t.resultTvs
                        }
                    }

                    override fun onError(e: Throwable) {
                        tvLoading.value = false
                        tvNotFound.value = false
                        tvError.value = true
                    }


                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}