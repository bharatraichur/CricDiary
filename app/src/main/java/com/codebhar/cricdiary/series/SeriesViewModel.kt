package com.codebhar.cricdiary.series

import android.app.Application
import androidx.lifecycle.*
import com.codebhar.cricdiary.database.CricSeriesData
import com.codebhar.cricdiary.database.getDatabase
import com.codebhar.cricdiary.repository.CricDiaryRepository
import kotlinx.coroutines.launch

class SeriesViewModel(application: Application) : AndroidViewModel(application) {

    enum class SeriesApiStatus { LOADING, ERROR, DONE }

    val seriesData: MediatorLiveData<List<CricSeriesData>> = MediatorLiveData()

    private val database = getDatabase(application)
    private val repository = CricDiaryRepository(database)

    private val _status = MutableLiveData(SeriesApiStatus.LOADING)
    val status: LiveData<SeriesApiStatus> = _status

    init {
        getSeriesList()
    }

    private fun getSeriesList() {
        viewModelScope.launch {
            _status.value = SeriesApiStatus.LOADING
            try {
                repository.fetchSeriesAndUpdateDB()
                setSeriesDataAndUpdateStatus(repository.seriesList)
            } catch (e: Exception) {
                _status.value = SeriesApiStatus.ERROR
            }
        }
    }

    private fun setSeriesDataAndUpdateStatus(data: LiveData<List<CricSeriesData>>) {
        seriesData.removeSource(data)
        seriesData.addSource(data) {
            seriesData.value = it
            if (it.isNotEmpty()) {
                _status.value = SeriesApiStatus.DONE
            }
        }
    }
}