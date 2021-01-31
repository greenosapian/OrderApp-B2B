package com.example.greenosapian.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vegie::class, CartItem::class, CartHistoryEntity::class],
    version = 8,
    exportSchema = false)
abstract class GreenDatabase: RoomDatabase() {

    abstract val dao:Dao

    companion object{
        @Volatile
        private var INSTANCE:GreenDatabase? = null

        fun getInstance(context: Context):GreenDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GreenDatabase::class.java,
                        "Database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}