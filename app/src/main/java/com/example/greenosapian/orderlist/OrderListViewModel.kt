package com.example.greenosapian.orderlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenosapian.bindImage
import com.example.greenosapian.database.CartItem
import com.example.greenosapian.database.Vegie
import kotlinx.coroutines.*

enum class ElasticApiStatus { LOADING, DONE, ERROR }

open class OrderListViewModel(private val repository: OrderRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<ElasticApiStatus>()
    val status: LiveData<ElasticApiStatus>
        get() = _status

    val veggies = repository.getVegetableList()

    init {
        getVegetableList()
    }

    private fun getVegetableList() {
        coroutineScope.launch {
            try {
                _status.value = ElasticApiStatus.LOADING
                //this delay is for smooth transition
                delay(900)
                repository.fetchVegetableListFromNetwork()
                _status.value = ElasticApiStatus.DONE
            } catch (t: Throwable) {
                _status.value = ElasticApiStatus.ERROR
            }
        }
    }

    fun addVeggieInCart(veggie: Vegie) {
        coroutineScope.launch {
            repository.updateVeggieQuantity(veggie.id, 1)
            repository.insertCartItem(CartItem(veggie.id))
        }
    }

    fun removeVeggieFromCart(veggie: Vegie?) {
        coroutineScope.launch {
            veggie?.let {
                repository.updateVeggieQuantity(veggie.id, 0)
                repository.removeCartItem(CartItem(veggie.id))
            }
        }
    }

    fun changeVeggieQuantity(veggie: Vegie, step: Int) {
        coroutineScope.launch {
            when (step) {
                1 -> {
                    repository.increaseQuantity(veggie.id)
                }
                -1 -> {
                    val prevQuantity = repository.getVeggie(veggie.id).quantity
                    if (prevQuantity == 1) {
                        removeVeggieFromCart(veggie)
                    } else {
                        repository.decreaseQuantity(veggie.id)
                    }
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}