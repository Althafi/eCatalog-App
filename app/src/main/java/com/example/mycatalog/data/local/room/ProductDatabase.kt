package com.example.mycatalog.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
//anotasi database untuk abstract class database dengan parameter entities, version dan exportschema
@Database(entities = [ProductFavoriteEntity::class], version = 2, exportSchema = false)

abstract class ProductDatabase: RoomDatabase() {

    //untuk memudahkan database mengakses class DAO
    abstract fun productDao(): ProductDao

    //
    companion object{
        //variable instance dengan anotasi volatile
        @Volatile
        private var INSTANCE : ProductDatabase? = null
        // function untuk getdatabase dari class productDatabase dengan variable instance
        fun getDatabase(context: Context): ProductDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                return instance
            }

        }
    }
}