package com.example.mycatalog.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mycatalog.data.local.entity.*
import com.example.mycatalog.data.paging.SIZE
import com.example.mycatalog.data.paging.STARTING_KEY
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ProductFavoriteEntity)

    @Query("SELECT * FROM $TABLE_PRODUCTS LIMIT :limit OFFSET :skip ")
    fun loadAll(skip: Int , limit: Int): List<ProductFavoriteEntity>

    @Query("SELECT * FROM $TABLE_PRODUCTS WHERE id= :id")
    fun getProductById(id: Int): Flow<ProductFavoriteEntity?>


    @Delete
    suspend fun delete(vararg item: ProductFavoriteEntity)


}