package com.codebhar.cricdiary.network.model

data class CricScore(
    val runs: Int,
    val wickets: Int,
    val overs: Long,
    val inning: String
)
