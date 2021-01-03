package com.example.greenosapian.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "accounts_table")
data class Account(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "phone_number")
    val phoneNumber:String,

    @ColumnInfo(name="isProfileSetupComplete")
    val isProfileSetupComplete:Boolean
)