package com.example.greenosapian.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "vegetables")
data class Vegie(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    var price: Long,

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,

    @ColumnInfo(name = "image_url")
    var imageUrl: String?
)

@Entity(
    tableName = "cart",
    foreignKeys = [
        ForeignKey(
            entity = Vegie::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
data class CartItem(
    @PrimaryKey(autoGenerate = false)
    val id: String
)