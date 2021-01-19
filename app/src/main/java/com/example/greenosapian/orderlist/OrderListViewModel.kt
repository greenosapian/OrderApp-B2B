package com.example.greenosapian.orderlist

import androidx.lifecycle.ViewModel
import com.example.greenosapian.database.CartItem
import com.example.greenosapian.database.Dao
import com.example.greenosapian.database.Vegie
import kotlinx.coroutines.*

open class OrderListViewModel(private val repository: OrderRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val veggies = repository.getVegetableList()

    fun addVeggieInCart(veggie: Vegie) {
        coroutineScope.launch {
            repository.updateVeggieQuantity(veggie.id, 1)
            repository.insertCartItem(CartItem(veggie.id))
        }
    }

    fun removeVeggieFromCart(veggie: Vegie) {
        coroutineScope.launch {
            repository.updateVeggieQuantity(veggie.id, 0)
            repository.removeCartItem(CartItem(veggie.id))
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