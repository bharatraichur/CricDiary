package com.codebhar.cricdiary.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.codebhar.cricdiary.BuildConfig
import com.codebhar.cricdiary.database.CricDiaryDatabase
import com.codebhar.cricdiary.database.CricMatchesData
import com.codebhar.cricdiary.database.CricSeriesData
import com.codebhar.cricdiary.network.CricDiaryAPI
import com.codebhar.cricdiary.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class CricDiaryRepository(private val database: CricDiaryDatabase) {

    private var API_KEY = BuildConfig.CRICDIARY_API_KEY

    suspend fun fetchSeriesAndUpdateDB() {
        withContext(Dispatchers.IO) {
            val response = NetworkUtils().parseSeriesInfoJsonResult(
                JSONObject(CricDiaryAPI.cricDiaryAPIService.getSeries(API_KEY)))
            database.cricDiaryDao().insertSeries(response)
        }
    }

    suspend fun fetchMatchesAndUpdateDB() {
        withContext(Dispatchers.IO) {
            val response = NetworkUtils().parseMatchesInfoJsonResult(
                JSONObject(CricDiaryAPI.cricDiaryAPIService.getMatches(API_KEY)))
            database.cricDiaryDao().insertMatches(response)
        }
    }

    val seriesList: LiveData<List<CricSeriesData>> = database.cricDiaryDao().getSeriesList().map { it }

    val t20SeriesList: LiveData<List<CricSeriesData>> = database.cricDiaryDao().getT20SeriesList().map { it }

    val currentMatchesList: LiveData<List<CricMatchesData>> = database.cricDiaryDao().getCurrentMatchesList().map { it }

    val recentMatchesList: LiveData<List<CricMatchesData>> = database.cricDiaryDao().getRecentMatchesList().map { it }
}