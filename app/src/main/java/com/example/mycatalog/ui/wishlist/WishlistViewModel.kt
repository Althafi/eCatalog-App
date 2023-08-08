package com.example.mycatalog.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.*

//class WishlistViewModel (private val appModule: AppModule): ViewModel(){
//
//    fun getUserFavorite() = appModule.productDao.loadAll()
//
//    class Factory(private val db: AppModule) : ViewModelProvider.NewInstanceFactory() {
//        @Suppress("UNCHECKED_CAST")
//        override fun <T : ViewModel> create(modelClass: Class<T>): T = WishlistViewModel(db) as T
//    }
//}