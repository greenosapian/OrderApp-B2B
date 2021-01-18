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
    fun insertVeggie(vegetableList: List<Vegie>)

    @Query("UPDATE vegetables SET quantity=:quantity WHERE id=:id")
    fun updateVeggieQuantity(id :String, quantity: Int)

    @Query("UPDATE vegetables SET quantity = quantity+1 WHERE id=:id")
    fun increaseQuantity(id :String)

    @Query("UPDATE vegetables SET quantity = quantity-1 WHERE id=:id and quantity>0")
    fun decreaseQuantity(id :String)

    @Query("SELECT * FROM vegetables")
    fun getCachedVegetableList(): LiveData<List<Vegie>>

    @Query("DELETE FROM vegetables")
    fun deleteAllCachedVegetables()

    @Query("SELECT COUNT(*) FROM vegetables")
    fun getVegetableCount():Long
}