package com.codebhar.cricdiary

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codebhar.cricdiary.matches.MatchesViewModel
import java.lang.IllegalArgumentException

class MatchesViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchesViewModel::class.java)) {
            return MatchesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}