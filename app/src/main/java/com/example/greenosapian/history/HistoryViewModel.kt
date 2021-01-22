package com.example.greenosapian.history

import androidx.lifecycle.ViewModel

class HistoryViewModel(private val repository: HistoryRepository) :ViewModel(){
    val historyList = repository.getHistoryList()

}