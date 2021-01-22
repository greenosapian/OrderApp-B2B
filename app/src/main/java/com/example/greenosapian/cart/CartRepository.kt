package com.example.greenosapian.cart

import androidx.annotation.WorkerThread
import com.example.greenosapian.database.CartHistoryEntity
import com.example.greenosapian.orderlist.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository(private val dao: com.example.greenosapian.database.Dao) : OrderRepository(dao) {

    private var cartItems = dao.getCartItems()

    fun getCartItems() = cartItems

    @WorkerThread
    suspend fun insertHistoryItem(historyEntity: CartHistoryEntity) = withContext(Dispatchers.IO) {
        dao.insertOrderHisoryItem(historyEntity)
    }

    @WorkerThread
    suspend fun clearCart() = withContext(Dispatchers.IO) {
        dao.clearCart()
        dao.clearVeggieQuantity()
    }
}