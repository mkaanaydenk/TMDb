package com.mehmetkaanaydenk.tmdb.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.tmdb.model.Movie
import com.mehmetkaanaydenk.tmdb.service.MovieAPIService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function4
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.function.BiConsumer
import java.util.function.BiFunction

class DiscoveryViewModel : ViewModel() {


    private val movieAPIService = MovieAPIService()

    val movies = MutableLiveData<List<Movie>>()

    var movie1: Movie? = null
    var movie2: Movie? = null
    var movie3: Movie? = null
    var movie4: Movie? = null


    fun getDataApi() {


        Single.zip(
            movieAPIService.getComingSoon(),
            movieAPIService.getAnimation(),
            movieAPIService.getPopular22(),
            movieAPIService.getHorror()
        ) { t1, t2, t3, t4 ->

            t1.titleName = "Yakında Vizyonda"
            t1.id = 100

            t2.titleName = "Birbirinden Güzel Animasyonlar"
            t2.id = 200

            t3.titleName = "2022'nin En İyileri"
            t3.id = 300

            t4.titleName = "Korku Gecesi"
            t4.id = 400

            movie1 = t1
            movie2 = t2
            movie3 = t3
            movie4 = t4


        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : SingleObserver<Unit> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    println(e.localizedMessage)
                }

                override fun onSuccess(t: Unit) {

                    var arrayList = ArrayList<Movie>()

                    movie1?.let { arrayList.add(it) }
                    movie2?.let { arrayList.add(it) }
                    movie3?.let { arrayList.add(it) }
                    movie4?.let { arrayList.add(it) }

                    movies.value = arrayList


                }


            })


        /*
        disposable.add(
            movieAPIService.getPopular22()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {

                        t.titleName = "2022'nin En İyileri"
                        addMovie(t)

                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }


                })


        )


        disposable.add(
            movieAPIService.getComingSoon()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {

                        t.titleName = "Yakında Vizyonda"
                        addMovie(t)

                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }


                })

        )

        disposable.add(
            movieAPIService.getHorror()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {

                        t.titleName = "Korku Gecesi"
                        addMovie(t)

                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }


                })

        )

        disposable.add(
            movieAPIService.getAnimation()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {

                        t.titleName = "Birbirinden Güzel Animasyonlar"
                        addMovie(t)

                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }


                })

        )




         */
    }


}