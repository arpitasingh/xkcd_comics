package com.xkcd.comics.viewer.app.api

import com.xkcd.comics.viewer.app.models.CurrentComicDetails
import com.xkcd.comics.viewer.app.util.Constants
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiServiceInterface {

    @GET("info.0.json")
    fun getCurrentComic(): Observable<CurrentComicDetails>

    @GET("{comicsId}/info.0.json")
    fun getComicsById(@Path("comicsId") comicsId: Long): Observable<CurrentComicDetails>

    companion object Factory {

        fun create(): ApiServiceInterface {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(
                    OkHttpClient
                        .Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        })
                        .build()
                )
                .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}