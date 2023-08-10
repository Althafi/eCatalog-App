package com.example.mycatalog.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.ui.list.ProductListViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WishlistViewModel (private val repository: ProductRepository): ViewModel(){

    val itemsFavorite : Flow<PagingData<Product>> = repository
        .getFavoriteProducts()
        .cachedIn(viewModelScope)

}

class WishlistViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WishlistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}