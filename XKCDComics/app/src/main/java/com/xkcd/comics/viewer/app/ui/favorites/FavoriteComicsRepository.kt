package com.xkcd.comics.viewer.app.ui.favorites

import android.app.Application
import androidx.lifecycle.LiveData
import com.xkcd.comics.viewer.app.database.ComicNote
import com.xkcd.comics.viewer.app.database.ComicsDao
import com.xkcd.comics.viewer.app.database.ComicsDatabase
import com.xkcd.comics.viewer.app.database.subscribeOnBackground

class FavoriteComicsRepository(application: Application) {

    private var noteDao: ComicsDao
    private var allNotes: LiveData<List<ComicNote>>

    private val database = ComicsDatabase.getInstance(application)

    init {
        noteDao = database.noteDao()
        allNotes = noteDao.getAllComicNotes()
    }

    fun insert(note: ComicNote) {
        subscribeOnBackground {
            noteDao.insert(note)
        }
    }

    fun update(note: ComicNote) {
        subscribeOnBackground {
            noteDao.update(note)
        }
    }

    fun delete(note: ComicNote) {
        subscribeOnBackground {
            noteDao.delete(note)
        }
    }

    fun deleteAllComics() {
        subscribeOnBackground {
            noteDao.deleteAllComicNotes()
        }
    }

    fun getAllComics(): LiveData<List<ComicNote>> {
        return allNotes
    }

    fun getComicsById(comicId: Int): LiveData<Int> {
        return noteDao.count(comicId)
    }
}