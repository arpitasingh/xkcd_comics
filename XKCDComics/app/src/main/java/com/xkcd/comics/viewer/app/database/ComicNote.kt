package com.xkcd.comics.viewer.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class ComicNote(
    val comicTitle: String,
    val comicDescription: String,
    val comicId: Int,
    val comicDate: String,
    val comicImageUrl: String,
    @PrimaryKey(autoGenerate = false) val id: Int? = null
)