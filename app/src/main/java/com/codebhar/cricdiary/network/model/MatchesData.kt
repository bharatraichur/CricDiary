package com.codebhar.cricdiary.network.model

import java.util.Date

data class MatchesData(
    val id: String,
    val name: String,
    val matchType: String,
    val score: ArrayList<CricScore>,
    val status: String,
    val venue: String,
    val date: Date,
    val dateTimeGMT: Date,
    val teams: ArrayList<String>,
    val series_id: String,
    val fantasyEnabled: Boolean
)
