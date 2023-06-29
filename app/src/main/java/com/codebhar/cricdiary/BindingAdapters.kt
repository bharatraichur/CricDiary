package com.codebhar.cricdiary

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codebhar.cricdiary.database.CricMatchesData
import com.codebhar.cricdiary.database.CricSeriesData
import com.codebhar.cricdiary.matches.MatchesAdapter
import com.codebhar.cricdiary.series.SeriesAdapter
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("listSeriesData")
fun bindRecyclerViewToSeries(recyclerView: RecyclerView, data: List<CricSeriesData>?) {
    data?.let {
        if (it.isNotEmpty()) {
            val adapter = recyclerView.adapter as SeriesAdapter
            adapter.submitList(data) {
                recyclerView.scrollToPosition(0)
            }
        }
    }
}

@BindingAdapter("listMatchesData")
fun bindRecyclerViewToMatches(recyclerView: RecyclerView, data: List<CricMatchesData>?) {
    data?.let {
        if (it.isNotEmpty()) {
            val adapter = recyclerView.adapter as MatchesAdapter
            adapter.submitList(data) {
                recyclerView.scrollToPosition(0)
            }
        }
    }
}

@BindingAdapter("intToText")
fun bindTextViewToInt(textView: AppCompatTextView, matches: Int) {
    textView.text = matches.toString()
}

@BindingAdapter("startDateToText")
fun bindTextViewToStartDate(textView: AppCompatTextView, dateString: String) {
    val context = textView.context
    val outputFormat = SimpleDateFormat("MMM dd yyyy", Locale.UK)
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
    formatter.parse(dateString)?.let {
        textView.text = if (System.currentTimeMillis() < it.time) {
            context.getString(R.string.starts, outputFormat.format(it))
        } else {
            context.getString(R.string.started_on, outputFormat.format(it))
        }
    }
}

@BindingAdapter("startDate", "endDate")
fun bindTextViewToEndDate(textView: AppCompatTextView, startDateString: String, endDateString: String) {
    var endDateStringWithYear = ""
    val context = textView.context
    try {
        val startDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        val startDateYear = SimpleDateFormat("YYYY", Locale.UK)
        startDateFormatter.parse(startDateString)?.let {
            val year = startDateYear.format(it)
            endDateStringWithYear = endDateString.plus(" $year")
        }

        val outputFormat = SimpleDateFormat("MMM dd yyyy", Locale.UK)
        val endDateFormatter = SimpleDateFormat("MMM dd yyyy", Locale.UK)
        endDateFormatter.parse(endDateStringWithYear)?.let {
            textView.text = if (System.currentTimeMillis() < it.time) {
                context.getString(R.string.ends, outputFormat.format(it))
            } else {
                context.getString(R.string.ended_on, outputFormat.format(it))
            }
        }
    } catch (exception: Exception) {
        exception.localizedMessage?.let { Log.e("CricDiary", it) }
    }
}

@BindingAdapter("teamName", "teamShortName")
fun bindTextViewToTeamName(textView: AppCompatTextView, teamName: String, teamShortName: String) {
    if (teamShortName.isNotEmpty()) {
        textView.text = teamShortName
    } else if (teamShortName.isEmpty() && teamName.isNotEmpty()) {
        textView.text = teamName
    }
}

@BindingAdapter("runs", "wickets")
fun bindTextViewToTeamScore(textView: AppCompatTextView, runs: Int, wickets: Int) {
    val context = textView.context
    if (runs == 0 && wickets == 0) {
        textView.isVisible = false
    } else {
        textView.text = context.getString(R.string.team_score, runs, wickets)
        textView.isVisible = true
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: AppCompatImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_cricket)
                .error(R.drawable.ic_cricket))
            .into(imgView)
    }
}

@BindingAdapter("liveMatch")
fun showLiveMatchIcon(textView: AppCompatTextView, showIcon: Boolean) {
    val context = textView.context
    if (showIcon) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_live, 0, 0, 0)
        textView.text = context.getString(R.string.live)
        textView.setTextColor(context.getColor(R.color.app_red))
    } else {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
        textView.text = context.getString(R.string.completed)
        textView.setTextColor(context.getColor(R.color.app_grey))
    }
}