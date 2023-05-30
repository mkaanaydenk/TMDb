package com.mehmetkaanaydenk.tmdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.tmdb.model.Cast
import com.mehmetkaanaydenk.tmdb.model.Credit
import com.mehmetkaanaydenk.tmdb.model.Crew
import com.mehmetkaanaydenk.tmdb.model.MovieDetail
import com.mehmetkaanaydenk.tmdb.model.Video
import com.mehmetkaanaydenk.tmdb.model.Videos
import com.mehmetkaanaydenk.tmdb.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieDetailFragmentModel : ViewModel() {

    private val disposable = CompositeDisposable()
    private val movieAPIService = MovieAPIService()

    val movieId = MutableLiveData<Int>()

    val movieDetail = MutableLiveData<MovieDetail>()

    val castList = MutableLiveData<List<Cast>>()

    val directorList = MutableLiveData<List<Crew>>()

    val producerList = MutableLiveData<List<Crew>>()

    val trailerList = MutableLiveData<List<Video>>()

    fun setMovieId(id: Int) {

        movieId.value = id

    }

    fun getMovieDetails() {

        disposable.add(

            movieAPIService.getMovieDetail(movieId.value!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetail>() {
                    override fun onSuccess(t: MovieDetail) {
                        movieDetail.value = t
                        trailerList.value = t.videos.videos
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }


                })

        )

        disposable.add(
            movieAPIService.getMovieCredit(movieId.value!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Credit>() {
                    override fun onSuccess(t: Credit) {

                        val directors = t.crew.filter { it.job == "Director" }
                        val producers = t.crew.filter { it.job == "Producer" }
                        directorList.value = directors
                        producerList.value = producers
                        castList.value = t.cast
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }


                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}