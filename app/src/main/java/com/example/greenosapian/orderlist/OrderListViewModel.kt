package com.example.greenosapian.orderlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenosapian.network.ElasticApi
import com.example.greenosapian.network.NetworkVegie
import kotlinx.coroutines.*
import java.lang.Exception

class OrderListViewModel :ViewModel(){

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _vegies = MutableLiveData<List<NetworkVegie>>()
    val vegies:LiveData<List<NetworkVegie>>
        get() = _vegies

    init {
        getVegetables()
    }

    private fun getVegetables() {
        coroutineScope.launch {
            try {
                _vegies.value = ElasticApi.retrofitService.getVegetables().outerHits.vegies
            }
            catch (e:Exception){
                println(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}