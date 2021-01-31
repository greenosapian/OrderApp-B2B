package com.example.greenosapian.homepage

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomePageViewModel(private val repository: HomePageRepository, private val app: Application) :
    AndroidViewModel(app) {

    private val _profilePicUri = MutableLiveData<Uri>()
    val profilePicUri: LiveData<Uri>
        get() = _profilePicUri

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        coroutineScope.launch {
            _profilePicUri.value = repository.getProfilePicUri(app)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}