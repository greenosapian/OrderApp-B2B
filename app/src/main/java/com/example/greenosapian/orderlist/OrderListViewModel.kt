package com.example.greenosapian.orderlist

import androidx.lifecycle.ViewModel
import com.example.greenosapian.database.Dao
import com.example.greenosapian.database.Vegie
import kotlinx.coroutines.*

class OrderListViewModel(private val repository: OrderRepository) :ViewModel(){

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val veggies = repository.getVegetableList()

    fun addVeggieInCart(veggie: Vegie){
        coroutineScope.launch {
            repository.updateVeggieQuantity(veggie.id, 1)

        }
    }

    fun removeVeggieFromCart(veggie: Vegie){
        coroutineScope.launch {
            repository.updateVeggieQuantity(veggie.id, 0)
        }
    }

    fun changeVeggieQuantity(veggie: Vegie, step:Int){
        coroutineScope.launch {
            when(step){
                1->{
                    repository.increaseQuantity(veggie.id)
                }
                -1->{
                    repository.decreaseQuantity(veggie.id)
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}