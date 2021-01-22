package com.example.greenosapian.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HistoryViewModelFactory(private val repository:HistoryRepository)  : ViewModelProvider.Factory {
    @SuppressWarnings("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown Viewmodel Class")
    }
}