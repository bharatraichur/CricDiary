package com.codebhar.cricdiary

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codebhar.cricdiary.series.SeriesViewModel
import java.lang.IllegalArgumentException

class SeriesViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeriesViewModel::class.java)) {
            return SeriesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}