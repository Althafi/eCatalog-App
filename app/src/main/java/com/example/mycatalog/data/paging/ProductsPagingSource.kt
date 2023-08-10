package com.example.mycatalog.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycatalog.data.mapper.toModel
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiService

const val STARTING_KEY = 0
const val SIZE = 30


//class untuk mengambil data dari API
class ProductsPagingSource (

    private val apiService : ApiService
    ) : PagingSource<Int, Product>() {

    //untuk menyediakan key yang akan digunakan untuk memuat PagingSource
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    //untuk mengambil lebih banyak data yang akan ditampilkan saat user melakukan scroll secara asinkron
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val pageNumber = params.key ?: STARTING_KEY
        val skip = pageNumber * SIZE
        val items = apiService.getProducts(SIZE, skip).toModel()


        return try {


            //jika hasilnya berhasil
            LoadResult.Page(
                //item dari data yang diambil
                data = items,
                //mengambil item di belakang halaman
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                //mengambil item setelah halaman
                nextKey = pageNumber.plus(1)
            )
        } catch (e: Exception) {
            //jika terjadi error
            LoadResult.Error(e)
        }
    }
}