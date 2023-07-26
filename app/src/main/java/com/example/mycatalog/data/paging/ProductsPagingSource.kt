package com.example.mycatalog.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycatalog.data.mapper.toModel
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiService

private const val STARTING_KEY = 0
const val SIZE = 30
const val SKIP = 30


class ProductsPagingSource (

    private val apiService : ApiService
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val pageNumber = params.key ?: STARTING_KEY
        val items = apiService.getProducts(SIZE, SKIP).toModel()


        return try {
            LoadResult.Page(
                data = items,
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                nextKey = pageNumber.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}