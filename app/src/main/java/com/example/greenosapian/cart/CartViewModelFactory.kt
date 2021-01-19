package com.example.greenosapian.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.greenosapian.orderlist.OrderListViewModel
import com.example.greenosapian.orderlist.OrderRepository

class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
    @SuppressWarnings("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown Viewmodel Class")
    }
}