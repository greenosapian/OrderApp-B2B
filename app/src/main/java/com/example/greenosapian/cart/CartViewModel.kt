package com.example.greenosapian.cart

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.greenosapian.orderlist.OrderListViewModel
import com.example.greenosapian.orderlist.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CartViewModel(private val repository: CartRepository) : OrderListViewModel(repository){
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val cartItemList = repository.getCartItems()

    val totalPrice = Transformations.map(cartItemList){cartItemList->
        if(cartItemList.isNullOrEmpty()) 0L
        else{
            var total = 0L
            for(veggie in cartItemList){
                total += veggie.quantity.toLong()*veggie.price
            }
            total
        }
    }

    fun onGoToMenuClicked(){
        println("go to menu")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}