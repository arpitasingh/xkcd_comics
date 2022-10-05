package com.xkcd.comics.viewer.app.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.xkcd.comics.viewer.app.database.ComicNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteComicsViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = FavoriteComicsRepository(app)
    private val allNotes = repository.getAllComics()

    fun insert(note: ComicNote) {
        repository.insert(note)
    }

    fun update(note: ComicNote) {
        repository.update(note)
    }

    fun delete(note: ComicNote) {
        repository.delete(note)
    }

    fun deleteAllComicss() {
        repository.deleteAllComics()
    }

    fun getAllComicss(): LiveData<List<ComicNote>> {
        return allNotes
    }

    fun isFavorite(comicId: Int): LiveData<Int> {
       return repository.getComicsById(comicId)
    }

    fun insertComic(comicNote: ComicNote) {
        viewModelScope.launch(Dispatchers.IO) {
                repository.insert(comicNote)

        }
    }
}