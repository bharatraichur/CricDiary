package com.codebhar.cricdiary.network.model

data class Series(
    val apikey: String,
    val data: List<SeriesData>,
    val info: Info,
    val status: String
)