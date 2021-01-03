package com.example.greenosapian.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao{
    @Query("SELECT * from accounts_table LIMIT 1")
    fun getUserAccount():Account?

    @Insert
    fun insertUser(userAccount: Account):Long

    @Update
    fun updateUserAccount(userAccount: Account)

    @Query("DELETE from accounts_table")
    fun deleteAllUserAccounts()
}