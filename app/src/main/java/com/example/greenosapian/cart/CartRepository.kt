package com.example.greenosapian.cart

import com.example.greenosapian.orderlist.OrderRepository

class CartRepository(private val dao: com.example.greenosapian.database.Dao) : OrderRepository(dao){

    private var cartItems = dao.getCartItems()

    fun getCartItems() = cartItems
}