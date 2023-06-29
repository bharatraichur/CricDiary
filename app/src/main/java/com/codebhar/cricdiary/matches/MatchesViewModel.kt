package com.codebhar.cricdiary.matches

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codebhar.cricdiary.database.CricMatchesData
import com.codebhar.cricdiary.database.getDatabase
import com.codebhar.cricdiary.repository.CricDiaryRepository
import kotlinx.coroutines.launch

class MatchesViewModel(application: Application): AndroidViewModel(application) {

    enum class MatchesApiStatus { LOADING, ERROR, DONE }

    enum class MatchesDataStatus { EMPTY, NOT_EMPTY }

    val currentMatchesData: MediatorLiveData<List<CricMatchesData>> = MediatorLiveData()
    private val recentMatchesData: MediatorLiveData<List<CricMatchesData>> = MediatorLiveData()

    private val database = getDatabase(application)
    private val repository = CricDiaryRepository(database)

    private val _dataStatus = MutableLiveData(MatchesDataStatus.NOT_EMPTY)
    val dataStatus: LiveData<MatchesDataStatus> = _dataStatus

    private val _status = MutableLiveData(MatchesApiStatus.LOADING)
    val status: LiveData<MatchesApiStatus> = _status

    init {
        getMatchesList()
    }

    private fun getMatchesList() {
        viewModelScope.launch {
            _status.value = MatchesApiStatus.LOADING
            try {
                repository.fetchMatchesAndUpdateDB()
                setCurrentMatchesDataAndUpdateStatus(repository.currentMatchesList)
            } catch (e: Exception) {
                _status.value = MatchesApiStatus.ERROR
            }
        }
    }

    private fun setCurrentMatchesDataAndUpdateStatus(data: LiveData<List<CricMatchesData>>) {
        currentMatchesData.removeSource(data)
        currentMatchesData.addSource(data) {
            currentMatchesData.value = it
            if (it.isNotEmpty()) {
                _status.value = MatchesApiStatus.DONE
            } else {
                _dataStatus.value = MatchesDataStatus.EMPTY
            }
        }
    }

    private fun setRecentMatchesDataAndUpdateStatus(data: LiveData<List<CricMatchesData>>) {
        recentMatchesData.removeSource(data)
        recentMatchesData.addSource(data) {
            recentMatchesData.value = it
            if (it.isNotEmpty()) {
                _status.value = MatchesApiStatus.DONE
            } else {
                _dataStatus.value = MatchesDataStatus.EMPTY
            }
        }
    }
}