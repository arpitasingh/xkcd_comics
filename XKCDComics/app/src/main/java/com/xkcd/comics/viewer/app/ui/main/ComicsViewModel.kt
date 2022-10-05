package com.xkcd.comics.viewer.app.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xkcd.comics.viewer.app.api.ComicsRepository
import com.xkcd.comics.viewer.app.models.CurrentComicDetails
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ComicsViewModel(private val repository: ComicsRepository) : ViewModel() {

    val comicsList = MutableLiveData<CurrentComicDetails>()
    val errorMessage = MutableLiveData<String>()
    lateinit var disposable: Disposable

    fun getAllComics() {
        //observer subscribing to observable
        val response = repository.getComics()
        response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getComicsListObserver())
    }

    private fun getComicsListObserver(): Observer<CurrentComicDetails> {
        return object : Observer<CurrentComicDetails> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                //throw error
            }

            override fun onNext(t: CurrentComicDetails) {
                comicsList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
                //start showing progress indicator.
            }
        }
    }
}