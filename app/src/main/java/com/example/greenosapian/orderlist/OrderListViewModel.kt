package com.example.greenosapian.orderlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenosapian.network.ElasticApi
import com.example.greenosapian.network.NetworkVegie
import kotlinx.coroutines.*
import java.lang.Exception

class OrderListViewModel(repository: OrderRepository) :ViewModel(){

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val veggies = repository.getVegetableList()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}