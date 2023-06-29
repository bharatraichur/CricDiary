package com.codebhar.cricdiary.utils

import android.util.Log
import com.codebhar.cricdiary.database.CricMatchesData
import com.codebhar.cricdiary.database.CricSeriesData
import org.json.JSONException
import org.json.JSONObject

class NetworkUtils {
    fun parseSeriesInfoJsonResult(jsonResult: JSONObject): List<CricSeriesData> {
        val seriesInfoJson = jsonResult.getJSONArray("data")

        val seriesList = mutableListOf<CricSeriesData>()

        for (i in 0 until seriesInfoJson.length()) {
            val seriesInfo = seriesInfoJson.getJSONObject(i)
            val endDate = seriesInfo.getString("endDate")
            val id = seriesInfo.getString("id")
            val matches = seriesInfo.getInt("matches")
            val name = seriesInfo.getString("name")
            val odi = seriesInfo.getInt("odi")
            val squads = seriesInfo.getInt("squads")
            val startDate = seriesInfo.getString("startDate")
            val t20 = seriesInfo.getInt("t20")
            val test = seriesInfo.getInt("test")

            val seriesInfoData = CricSeriesData(
                endDate,
                id,
                matches,
                name,
                odi,
                squads,
                startDate,
                t20,
                test
            )

            seriesList.add(seriesInfoData)
        }

        return seriesList
    }

    fun parseMatchesInfoJsonResult(jsonResult: JSONObject): List<CricMatchesData> {
        val matchesInfoJson = jsonResult.getJSONArray("data")

        val matchesList = mutableListOf<CricMatchesData>()

        for (i in 0 until matchesInfoJson.length()) {
            val matchesInfo = matchesInfoJson.getJSONObject(i)
            var id = ""
            var name = ""
            var matchType = ""
            var status = ""
            var venue = ""
            var date = ""
            var team1 = ""
            var team2 = ""
            var team1Name = ""
            var team1ShortName = ""
            var team1Img = ""
            var team2Name = ""
            var team2ShortName = ""
            var team2Img = ""
            var dateTimeGMT = ""
            var seriesId = ""
            var fantasyEnabled = false
            var bbbEnabled = false
            var hasSquad = false
            var matchStarted = false
            var matchEnded = false
            var team1Inning1Runs = 0
            var team1Inning1Wickets = 0
            var team1Inning1Overs = 0.0
            var team1Inning1Inning = ""
            var team2Inning1Runs = 0
            var team2Inning1Wickets = 0
            var team2Inning1Overs = 0.0
            var team2Inning1Inning = ""
            var team1Inning2Runs = 0
            var team1Inning2Wickets = 0
            var team1Inning2Overs = 0.0
            var team1Inning2Inning = ""
            var team2Inning2Runs = 0
            var team2Inning2Wickets = 0
            var team2Inning2Overs = 0.0
            var team2Inning2Inning = ""
            try {
                id = matchesInfo.getString("id")
                name = matchesInfo.getString("name")
                matchType = matchesInfo.getString("matchType")
                status = matchesInfo.getString("status")
                venue = matchesInfo.getString("venue")
                date = matchesInfo.getString("date")
                val teams = matchesInfo.getJSONArray("teams")
                val teamInfo = matchesInfo.getJSONArray("teamInfo")
                val score = matchesInfo.getJSONArray("score")

                var hasTeam1Set = false
                var hasTeam1ScoreSet = false
                var hasTeam1Inning1ScoreSet = false
                var hasTeam2ScoreSet = false
                var hasTeam2Inning1ScoreSet = false

                for (m in 0 until teams.length()) {
                    val team = teams.get(m).toString()

                    for (n in 0 until teamInfo.length()) {
                        val teamInfoDetail = teamInfo.get(n) as JSONObject
                        if (team == teamInfoDetail.getString("name")) {
                            if (!hasTeam1Set) {
                                team1 = team
                                team1Name = teamInfoDetail.getString("name")
                                team1ShortName = teamInfoDetail.getString("shortname")
                                team1Img = teamInfoDetail.getString("img")
                                hasTeam1Set = true
                            } else {
                                team2 = team
                                team2Name = teamInfoDetail.getString("name")
                                team2ShortName = teamInfoDetail.getString("shortname")
                                team2Img = teamInfoDetail.getString("img")
                            }
                        }
                    }

                    for (p in 0 until score.length()) {
                        val scoreDetail = score.get(p) as JSONObject
                        if (scoreDetail.getString("inning").contains(team)) {
                            if (!hasTeam1ScoreSet) {
                                if (!hasTeam1Inning1ScoreSet) {
                                    team1Inning1Runs = scoreDetail.getInt("r") ?: 0
                                    team1Inning1Wickets = scoreDetail.getInt("w") ?: 0
                                    team1Inning1Overs = scoreDetail.getDouble("o") ?: 0.0
                                    team1Inning1Inning = scoreDetail.getString("inning") ?: ""
                                    hasTeam1Inning1ScoreSet = true
                                } else {
                                    team1Inning2Runs = scoreDetail.getInt("r") ?: 0
                                    team1Inning2Wickets = scoreDetail.getInt("w") ?: 0
                                    team1Inning2Overs = scoreDetail.getDouble("o") ?: 0.0
                                    team1Inning2Inning = scoreDetail.getString("inning") ?: ""
                                    hasTeam1ScoreSet = true
                                }
                                if (score.length() <= 2) {
                                    hasTeam1ScoreSet = true
                                }
                            } else if (!hasTeam2ScoreSet){
                                if (!hasTeam2Inning1ScoreSet) {
                                    team2Inning1Runs = scoreDetail.getInt("r") ?: 0
                                    team2Inning1Wickets = scoreDetail.getInt("w") ?: 0
                                    team2Inning1Overs = scoreDetail.getDouble("o") ?: 0.0
                                    team2Inning1Inning = scoreDetail.getString("inning") ?: ""
                                    hasTeam2Inning1ScoreSet = true
                                } else {
                                    team2Inning2Runs = scoreDetail.getInt("r") ?: 0
                                    team2Inning2Wickets = scoreDetail.getInt("w") ?: 0
                                    team2Inning2Overs = scoreDetail.getDouble("o") ?: 0.0
                                    team2Inning2Inning = scoreDetail.getString("inning") ?: ""
                                    hasTeam2ScoreSet = true
                                }
                                if (score.length() <= 2) {
                                    hasTeam2ScoreSet = true
                                }
                            }
                        }
                    }
                }

                dateTimeGMT = matchesInfo.getString("dateTimeGMT")
                seriesId = matchesInfo.getString("series_id")
                fantasyEnabled = matchesInfo.getBoolean("fantasyEnabled")
                bbbEnabled = matchesInfo.getBoolean("bbbEnabled")
                hasSquad = matchesInfo.getBoolean("hasSquad")
                matchStarted = matchesInfo.getBoolean("matchStarted")
                matchEnded = matchesInfo.getBoolean("matchEnded")
            } catch (exception: JSONException) {
                Log.i(NetworkUtils::class.simpleName, "Missing data for Matches")
            }

            val matchesInfoData = CricMatchesData(
                id,
                name,
                matchType,
                status,
                venue,
                date,
                dateTimeGMT,
                team1,
                team2,
                team1Name,
                team1ShortName,
                team1Img,
                team2Name,
                team2ShortName,
                team2Img,
                team1Inning1Runs,
                team1Inning1Wickets,
                team1Inning1Overs,
                team1Inning1Inning,
                team2Inning1Runs,
                team2Inning1Wickets,
                team2Inning1Overs,
                team2Inning1Inning,
                team1Inning2Runs,
                team1Inning2Wickets,
                team1Inning2Overs,
                team1Inning2Inning,
                team2Inning2Runs,
                team2Inning2Wickets,
                team2Inning2Overs,
                team2Inning2Inning,
                seriesId,
                fantasyEnabled,
                bbbEnabled,
                hasSquad,
                matchStarted,
                matchEnded
            )

            matchesList.add(matchesInfoData)
        }

        return matchesList
    }
}