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
            fetchVegetableListFromNetwork()
        }
    }

    fun getVegetableList() = veggieList

    @WorkerThread
    private suspend fun fetchVegetableListFromNetwork() {

        if (dao.getVegetableCount() != 0L) {
            return
        }

        val networkVeggies: List<NetworkVegie>? = try {
            networkService.getVegetables().outerHits.vegies
        } catch (e: Exception) {
            null
        }

        networkVeggies?.let {
            val vegetableList: MutableList<Vegie> = mutableListOf()
            for (networkVeggie in it) {

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
    }

    @WorkerThread
    suspend fun updateVeggieQuantity(id: String, quantity: Int) {
        withContext(Dispatchers.IO) {
            dao.updateVeggieQuantity(id, quantity)
        }
    }

    @WorkerThread
    suspend fun increaseQuantity(id: String) = withContext(Dispatchers.IO) {
        dao.increaseQuantity(id)
    }

    @WorkerThread
    suspend fun decreaseQuantity(id: String) = withContext(Dispatchers.IO) {
        dao.decreaseQuantity(id)
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