package com.xkcd.comics.viewer.app.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ComicsDao {

    @Insert
    fun insert(note: ComicNote)

    @Update
    fun update(note: ComicNote)

    @Delete
    fun delete(note: ComicNote)

    @Query("delete from note_table")
    fun deleteAllComicNotes()

    @Query("select * from note_table order by comicId desc")
    fun getAllComicNotes(): LiveData<List<ComicNote>>

    @Query("SELECT COUNT() from note_table WHERE comicId = :comicId")
    fun count(comicId: Int): LiveData<Int>
}