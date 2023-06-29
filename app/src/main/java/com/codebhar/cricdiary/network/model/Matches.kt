package com.codebhar.cricdiary.network.model

data class Matches(
    val apikey: String,
    val data: List<MatchesData>,
    val info: Info,
    val status: String
)