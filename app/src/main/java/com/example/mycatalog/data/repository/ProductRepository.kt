package com.example.mycatalog.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
import com.example.mycatalog.data.local.room.ProductDao
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.mapper.toModel
import com.example.mycatalog.data.mapper.toProductFavoriteEntity
import com.example.mycatalog.data.paging.ProductsPagingSource
import com.example.mycatalog.data.paging.SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ProductRepository(
    private val service: ApiService,
    private val productDao : ProductDao

) {

    fun getFavorite(id: Int): Flow<Product?>{
        return productDao.getProductById(id).map {
            it?.toModel()
        }

    }

    suspend fun setFavoriteDelete(product: Product) {
        return productDao.delete(product.toProductFavoriteEntity())
    }

    suspend fun setFavoriteInsert(product: Product) {
        return productDao.insert(product.toProductFavoriteEntity())
    }

    suspend fun getProduct(id: Int): Product{
        return service.getProduct(id).toModel()
    }

    //untuk menangani cache dalam memori dan meminta data saat user ada di akhir page
   fun getProducts(): Flow<PagingData<Product>> {
       return Pager (
           config = PagingConfig(pageSize = SIZE, enablePlaceholders = false),
           pagingSourceFactory = {ProductsPagingSource(service)}
               )
           .flow
   }




}