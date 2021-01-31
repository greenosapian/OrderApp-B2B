package com.example.greenosapian.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    //    @Query("SELECT * from accounts_table LIMIT 1")
//    fun getUserAccount():Account?
//
//    @Insert
//    fun insertUser(userAccount: Account):Long
//
//    @Update
//    fun updateUserAccount(userAccount: Account)
//
//    @Query("DELETE from accounts_table")
//    fun deleteAllUserAccounts()
    @Insert
    fun insertOrderHisoryItem(historyEntity: CartHistoryEntity)

    @Insert
    fun insertVeggie(vegetableList: List<Vegie>)

    @Insert
    fun insetCartItem(cartItem: CartItem)

    @Delete
    fun removeCartItem(cartItem: CartItem)

    @Query("UPDATE vegetables SET quantity=:quantity WHERE id=:id")
    fun updateVeggieQuantity(id :String, quantity: Int)

    @Query("UPDATE vegetables SET quantity = quantity+1 WHERE id=:id")
    fun increaseQuantity(id :String)

    @Query("UPDATE vegetables SET quantity = quantity-1 WHERE id=:id and quantity>0")
    fun decreaseQuantity(id :String)

    @Query("UPDATE vegetables SET total_price= quantity*price WHERE id=:id")
    fun updateTotalPrice(id :String)

    @Query("SELECT * FROM vegetables")
    fun getCachedVegetableList(): LiveData<List<Vegie>>

    @Query("SELECT * FROM vegetables WHERE id in (SELECT * FROM cart)")
    fun getCartItems():LiveData<List<Vegie>>

    @Query("DELETE FROM vegetables")
    fun deleteAllCachedVegetables()

    @Query("SELECT COUNT(*) FROM vegetables")
    fun getVegetableCount():Long

    @Query("SELECT * FROM vegetables WHERE id =:id")
    fun getVeggie(id:String):Vegie

    @Query("SELECT * FROM carthistory ORDER BY time DESC")
    fun getHistory():LiveData<List<CartHistoryEntity>>

    @Query("UPDATE vegetables SET quantity=0")
    fun clearVeggieQuantity()

    @Query("DELETE FROM cart")
    fun clearCart()
}