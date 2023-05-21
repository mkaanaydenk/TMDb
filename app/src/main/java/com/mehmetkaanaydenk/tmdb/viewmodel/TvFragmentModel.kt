package com.mehmetkaanaydenk.tmdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.tmdb.model.ResultTv
import com.mehmetkaanaydenk.tmdb.model.Tv
import com.mehmetkaanaydenk.tmdb.model.TvGenre
import com.mehmetkaanaydenk.tmdb.model.TvGenres
import com.mehmetkaanaydenk.tmdb.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TvFragmentModel : ViewModel() {

    private val disposable = CompositeDisposable()
    private val movieAPIService = MovieAPIService()

    val tvSeries = MutableLiveData<List<ResultTv>>()

    val tvGenres = MutableLiveData<List<TvGenre>>()

    val selectedGenreId = MutableLiveData<String>("10759")

    val selectedSortBy = MutableLiveData<String>()

    val includeAdult = MutableLiveData<Boolean>(false)

    val progressBar = MutableLiveData<Boolean>()

    val tvError = MutableLiveData<Boolean>()

    val recyclerView = MutableLiveData<Boolean>()

    fun setGenre(genre: String) {

        selectedGenreId.value = genre

    }

    fun setSort(sortBy: String) {

        selectedSortBy.value = sortBy

    }

    fun setAdult(adult: Boolean) {

        includeAdult.value = adult

    }

    fun getTvs(genre: String, sort: String, includeAdult: Boolean) {

        progressBar.value = true

        disposable.add(

            movieAPIService.getTvFragmentTvs(genre, sort, includeAdult)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Tv>() {
                    override fun onSuccess(t: Tv) {
                        progressBar.value = false
                        tvError.value = false
                        recyclerView.value = true
                        tvSeries.value = t.resultTvs
                    }

                    override fun onError(e: Throwable) {
                        progressBar.value = false
                        recyclerView.value = false
                        tvError.value = true
                    }


                })

        )

    }


    fun getGenres() {

        disposable.add(

            movieAPIService.getTvGenres()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvGenres>() {
                    override fun onSuccess(t: TvGenres) {
                        tvGenres.value = t.tvGenres
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                    }


                })

        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}