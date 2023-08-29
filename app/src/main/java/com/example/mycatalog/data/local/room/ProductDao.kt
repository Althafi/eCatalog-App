package com.example.mycatalog.data.local.room

    import androidx.room.*
import com.example.mycatalog.data.local.entity.*
import kotlinx.coroutines.flow.Flow

//anotasi untuk berinteraksi ke database aplikasi
@Dao
interface ProductDao {


    //metode untuk insert parameter ke dalam tabel yang sesuai di database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ProductFavoriteEntity)

    //metode yang menggunakan query select untuk menampilkan objek table_products dengan limit dan offset
    @Query("SELECT * FROM $TABLE_PRODUCTS LIMIT :limit OFFSET :skip ")
    fun loadAll(skip: Int , limit: Int): List<ProductFavoriteEntity>

    //metode yang menggunakan query select untuk menampilkan objek table_products dengan where id
    @Query("SELECT * FROM $TABLE_PRODUCTS WHERE id= :id")
    fun getProductById(id: Int): Flow<ProductFavoriteEntity?>

    //metode untuk delete baris tertentu dari table di database
    @Delete
    suspend fun delete(vararg item: ProductFavoriteEntity)


}