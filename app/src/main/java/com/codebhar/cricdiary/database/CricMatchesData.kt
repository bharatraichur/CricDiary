package com.codebhar.cricdiary.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "matches_data_table")
data class CricMatchesData(
    @PrimaryKey
    val id: String,
    val name: String,
    val matchType: String,
    val status: String,
    val venue: String,
    val date: String,
    val dateTimeGMT: String,
    val team1: String,
    val team2: String,
    val team1Name: String,
    val team1ShortName: String,
    val team1img: String,
    val team2Name: String,
    val team2ShortName: String,
    val team2img: String,
    val team1Inning1Runs: Int,
    val team1Inning1Wickets: Int,
    val team1Inning1Overs: Double,
    val team1Inning1Inning: String,
    val team2Inning1Runs: Int,
    val team2Inning1Wickets: Int,
    val team2Inning1Overs: Double,
    val team2Inning1Inning: String,
    val team1Inning2Runs: Int,
    val team1Inning2Wickets: Int,
    val team1Inning2Overs: Double,
    val team1Inning2Inning: String,
    val team2Inning2Runs: Int,
    val team2Inning2Wickets: Int,
    val team2Inning2Overs: Double,
    val team2Inning2Inning: String,
    val series_id: String,
    val fantasyEnabled: Boolean,
    val bbbEnabled: Boolean,
    val hasSquad: Boolean,
    val matchStarted: Boolean,
    val matchEnded: Boolean
) : Parcelable
