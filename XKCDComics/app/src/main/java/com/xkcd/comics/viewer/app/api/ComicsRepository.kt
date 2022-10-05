package com.xkcd.comics.viewer.app.api

class ComicsRepository constructor(private val apiService: ApiServiceInterface) {
    fun getComics() = apiService.getCurrentComic()
    fun getComicsById(id: Long) = apiService.getComicsById(id)
}