package com.example.greenosapian.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vegetables")
data class Vegie(
    @PrimaryKey(autoGenerate = false)
    val id:String,

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "price")
    val price:Long,

    @ColumnInfo(name = "quantity")
    val quantity:Int = 0,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?
)