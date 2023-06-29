package com.codebhar.cricdiary.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CricDiaryDAO {
    @Query("SELECT * FROM series_data_table ORDER BY startDate ASC")
    fun getSeriesList(): LiveData<List<CricSeriesData>>

    @Query("SELECT * FROM series_data_table WHERE t20 != 0")
    fun getT20SeriesList(): LiveData<List<CricSeriesData>>

    @Query("SELECT * FROM matches_data_table WHERE matchStarted=1 ORDER BY matchEnded=1")
    fun getCurrentMatchesList(): LiveData<List<CricMatchesData>>

    @Query("SELECT * FROM matches_data_table WHERE matchStarted=1 ORDER BY matchEnded=1")
    fun getRecentMatchesList(): LiveData<List<CricMatchesData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(series: List<CricSeriesData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatches(series: List<CricMatchesData>)
}