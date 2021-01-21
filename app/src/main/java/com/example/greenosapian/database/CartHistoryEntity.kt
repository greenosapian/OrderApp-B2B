package com.example.greenosapian.database

import androidx.room.*
import com.google.gson.Gson

@Entity(tableName = "CartHistory")
@TypeConverters(Converters::class)
class CartHistoryEntity (
    @PrimaryKey
    val time:Long,

    @ColumnInfo(name = "totalPrice")
    val totalPrice:Long,

    @ColumnInfo(name="itemList")
    val itemList:List<Vegie>
)

class Converters{
    @TypeConverter
    fun listToJson(value: List<Vegie>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<Vegie> = Gson().fromJson(value, Array<Vegie>::class.java).toList()
}