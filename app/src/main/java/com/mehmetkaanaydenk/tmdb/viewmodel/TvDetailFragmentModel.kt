package com.mehmetkaanaydenk.tmdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.tmdb.model.Cast
import com.mehmetkaanaydenk.tmdb.model.Credit
import com.mehmetkaanaydenk.tmdb.model.Season
import com.mehmetkaanaydenk.tmdb.model.TvDetail
import com.mehmetkaanaydenk.tmdb.model.Video
import com.mehmetkaanaydenk.tmdb.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TvDetailFragmentModel : ViewModel() {

    private val disposable = CompositeDisposable()
    private val movieAPIService = MovieAPIService()

    private val tvId = MutableLiveData<Int>()

    val tvDetail = MutableLiveData<TvDetail>()

    val castList = MutableLiveData<List<Cast>>()

    val trailerList = MutableLiveData<List<Video>>()

    val seasonList = MutableLiveData<List<Season>>()

    fun setTvId(id: Int) {

        tvId.value = id

    }

    fun getTvDetail() {

        disposable.add(
            movieAPIService.getTvDetail(tvId.value!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvDetail>() {
                    override fun onSuccess(t: TvDetail) {
                        tvDetail.value = t

                        val videos = t.videos.videos.filter { it.site == "YouTube" }

                        trailerList.value = videos
                        seasonList.value = t.seasons
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }


                })
        )

        disposable.add(

            movieAPIService.getTvCredit(tvId.value!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Credit>() {
                    override fun onSuccess(t: Credit) {
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