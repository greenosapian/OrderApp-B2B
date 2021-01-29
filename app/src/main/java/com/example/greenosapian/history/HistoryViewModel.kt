package com.example.greenosapian.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    val historyList = repository.getHistoryList()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _isDatabaseLoading = MutableLiveData<Boolean>()
    val isDatabaseLoading: LiveData<Boolean>
        get() = _isDatabaseLoading

    init {
        delayShowingList()
    }

    private fun delayShowingList() {
        coroutineScope.launch {
            _isDatabaseLoading.value = true
            delay(1000)
            _isDatabaseLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
