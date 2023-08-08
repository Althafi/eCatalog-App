package com.example.mycatalog.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mycatalog.data.local.entity.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ProductFavoriteEntity)

    @Query("SELECT * FROM $TABLE_PRODUCTS")
    fun loadAll(): LiveData<List<ProductFavoriteEntity>>

    @Query("SELECT * FROM $TABLE_PRODUCTS WHERE id= :id")
    fun getProductById(id: Int): Flow<ProductFavoriteEntity?>

//    @Query("SELECT * from $TABLE_PRODUCTS ORDER BY title ASC")
//    fun getItems(): Flow<List<ProductEntity>>

    @Delete
    suspend fun delete(vararg item: ProductFavoriteEntity)


}