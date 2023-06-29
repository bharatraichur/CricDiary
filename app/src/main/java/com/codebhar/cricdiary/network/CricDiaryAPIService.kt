package com.codebhar.cricdiary.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.cricapi.com/v1/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
    .build()

interface CricDiaryAPIService {
    @GET("series")
    suspend fun getSeries(
        @Query("apikey") apikey: String,
        @Query("offset") offset: Int = 0
    ) : String

    @GET("currentMatches")
    suspend fun getMatches(
        @Query("apikey") apikey: String,
        @Query("offset") offset: Int = 0
    ) : String
}

object CricDiaryAPI {
    val cricDiaryAPIService: CricDiaryAPIService by lazy { retrofit.create(CricDiaryAPIService::class.java) }
}