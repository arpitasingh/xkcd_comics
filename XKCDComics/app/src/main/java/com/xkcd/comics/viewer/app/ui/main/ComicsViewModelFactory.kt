package com.xkcd.comics.viewer.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xkcd.comics.viewer.app.api.ComicsRepository

class ComicsViewModelFactory constructor(private val repository: ComicsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ComicsViewModel::class.java)) {
            ComicsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}