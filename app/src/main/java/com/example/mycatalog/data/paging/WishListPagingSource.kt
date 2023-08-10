package com.example.mycatalog.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
import com.example.mycatalog.data.local.room.ProductDao
import com.example.mycatalog.data.mapper.toModel
import com.example.mycatalog.data.model.Product


class WishListPagingSource(private val productDao: ProductDao
): PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val pageNumber = params.key ?: STARTING_KEY
        val skip = pageNumber * SIZE
        val items = productDao.loadAll(skip, SIZE).map { it.toModel() }


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
        }    }

}