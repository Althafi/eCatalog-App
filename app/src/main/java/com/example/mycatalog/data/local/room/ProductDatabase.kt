package com.example.mycatalog.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mycatalog.data.local.entity.ProductFavoriteEntity

@Database(entities = [ProductFavoriteEntity::class], version = 2, exportSchema = false)

abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object{
        @Volatile
        private var INSTANCE : ProductDatabase? = null
        fun getDatabase(context: Context): ProductDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE=instance
                return instance
            }

        }
    }
}