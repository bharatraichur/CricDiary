package com.codebhar.cricdiary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CricSeriesData::class, CricMatchesData::class], version = 1)
abstract class CricDiaryDatabase : RoomDatabase() {
    abstract fun cricDiaryDao(): CricDiaryDAO
}

private lateinit var INSTANCE: CricDiaryDatabase

fun getDatabase(context: Context): CricDiaryDatabase {
    if (!::INSTANCE.isInitialized) {
        INSTANCE = Room
            .databaseBuilder(
            context.applicationContext,
            CricDiaryDatabase::class.java,
            "cric-diary-database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    return INSTANCE
}