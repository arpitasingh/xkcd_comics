package com.xkcd.comics.viewer.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ComicNote::class], version = 1)
abstract class ComicsDatabase : RoomDatabase() {

    abstract fun noteDao(): ComicsDao

    companion object {
        private var instance: ComicsDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ComicsDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, ComicsDatabase::class.java,
                    "comics_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }


}