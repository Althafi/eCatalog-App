package com.example.mycatalog.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.mapper.toModel
import com.example.mycatalog.data.paging.ProductsPagingSource
import com.example.mycatalog.data.paging.SIZE
import kotlinx.coroutines.flow.Flow


class ProductRepository(
    private val service: ApiService
) {


    suspend fun getProduct(id: Int): Product{
        return service.getProduct(id).toModel()
    }

   fun getProducts(): Flow<PagingData<Product>> {
       return Pager (
           config = PagingConfig(pageSize = SIZE, enablePlaceholders = false),
           pagingSourceFactory = {ProductsPagingSource(service)}
               )
           .flow
   }


}