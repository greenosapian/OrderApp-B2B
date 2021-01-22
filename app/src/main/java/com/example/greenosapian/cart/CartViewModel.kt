package com.example.greenosapian.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.greenosapian.database.CartHistoryEntity
import com.example.greenosapian.orderlist.OrderListViewModel
import com.example.greenosapian.orderlist.OrderRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : OrderListViewModel(repository) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val cartItemList = repository.getCartItems()

    private val _navigateToHomePageFragment = MutableLiveData<String>()
    val navigateToHomePageFragment: LiveData<String>
        get() = _navigateToHomePageFragment

    private val _orderPlacedDialogVisibility = MutableLiveData<Boolean>()
    val orderPlacedDialogVisibility: LiveData<Boolean>
        get() = _orderPlacedDialogVisibility

    val totalPrice = Transformations.map(cartItemList) { cartItemList ->
        if (cartItemList.isNullOrEmpty()) 0L
        else {
            var total = 0L
            for (veggie in cartItemList) {
                total += veggie.quantity.toLong() * veggie.price
            }
            total
        }
    }

    fun onPlaceOrderClicked(){
        _orderPlacedDialogVisibility.value = true
        coroutineScope.launch {
            repository.insertHistoryItem(CartHistoryEntity(System.currentTimeMillis(), totalPrice.value!!, cartItemList.value!!))
            repository.clearCart()
        }
    }

    fun onGoToMenuClicked() {
        val phoneNumber:String = Firebase.auth.currentUser?.phoneNumber!!
        _navigateToHomePageFragment.value = phoneNumber
    }

    fun onNavigateToHomePageCompleted(){
        _navigateToHomePageFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}