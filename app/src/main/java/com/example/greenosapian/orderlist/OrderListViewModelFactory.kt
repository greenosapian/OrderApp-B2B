package com.example.greenosapian.orderlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrderListViewModelFactory(private val repository: OrderRepository) : ViewModelProvider.Factory {
    @SuppressWarnings("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OrderListViewModel::class.java)) {
            return OrderListViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown Viewmodel Class")
    }
}