package com.example.greenosapian.orderlist

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greenosapian.database.CartItem
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.network.ElasticApi
import com.example.greenosapian.network.NetworkVegie
import kotlinx.coroutines.*
import java.lang.Exception

open class OrderRepository(private val dao: com.example.greenosapian.database.Dao) {
    private val networkService = ElasticApi.retrofitService

    private var veggieList = dao.getCachedVegetableList()

    init {
        CoroutineScope(Dispatchers.IO).launch {
//            fetchVegetableListFromNetwork()
        }
    }

    fun getVegetableList() = veggieList

    @WorkerThread
    suspend fun fetchVegetableListFromNetwork() = withContext(Dispatchers.IO){

        if (dao.getVegetableCount() != 0L) {
            return@withContext
        }

        val networkVeggies = networkService.getVegetables().outerHits.vegies

        val vegetableList: MutableList<Vegie> = mutableListOf()
        for (networkVeggie in networkVeggies) {

            vegetableList.add(
                Vegie(
                    networkVeggie.id,
                    networkVeggie.value.name,
                    networkVeggie.value.price,
                    0,
                    networkVeggie.value.imageUrl
                )
            )
        }
        dao.insertVeggie(vegetableList)
    }

    @WorkerThread
    suspend fun updateVeggieQuantity(id: String, quantity: Int) {
        withContext(Dispatchers.IO) {
            dao.updateVeggieQuantity(id, quantity)
            dao.updateTotalPrice(id)
        }
    }

    @WorkerThread
    suspend fun increaseQuantity(id: String) = withContext(Dispatchers.IO) {
        dao.increaseQuantity(id)
        dao.updateTotalPrice(id)
    }

    @WorkerThread
    suspend fun decreaseQuantity(id: String) = withContext(Dispatchers.IO) {
        dao.decreaseQuantity(id)
        dao.updateTotalPrice(id)
    }

    @WorkerThread
    suspend fun getVeggie(id: String) = withContext(Dispatchers.IO) {
        dao.getVeggie(id)
    }

    suspend fun insertCartItem(cartItem: CartItem) = withContext(Dispatchers.IO) {
        dao.insetCartItem(cartItem)
    }

    suspend fun removeCartItem(cartItem: CartItem) = withContext(Dispatchers.IO) {
        dao.removeCartItem(cartItem)
    }
}