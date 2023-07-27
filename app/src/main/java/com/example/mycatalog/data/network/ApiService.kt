package com.example.mycatalog.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//untuk mengambil data products dan id product pada API
interface ApiService {
        @GET("products")
        suspend fun getProducts(
            @Query("size") size: Int,
            @Query("skip") skip: Int
        ): ProductsResponse

        @GET("products/{id}")
        suspend fun getProduct(
            @Path("id") id: Int
        ): ProductsItemResponse
    }
