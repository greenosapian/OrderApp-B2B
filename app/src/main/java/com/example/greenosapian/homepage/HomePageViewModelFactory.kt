package com.example.greenosapian.homepage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.greenosapian.history.HistoryRepository
import com.example.greenosapian.history.HistoryViewModel

class HomePageViewModelFactory(private val repository: HomePageRepository, private val app: Application)  : ViewModelProvider.Factory {
    @SuppressWarnings("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            return HomePageViewModel(repository, app) as T
        }
        throw  IllegalArgumentException("Unknown Viewmodel Class")
    }
}