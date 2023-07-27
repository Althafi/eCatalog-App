package com.example.mycatalog.ui.list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiConfig
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repo: ProductRepository,
    private val userPreferences: UserPreferences

): ViewModel()  {


    val items : Flow<PagingData<Product>> = repo
        .getProducts()
        .cachedIn(viewModelScope)


    val userPreferencesFlow = userPreferences.userPreferencesFlow.asLiveData()
    fun authLogin(status: Boolean, email : String ){

        viewModelScope.launch {
            userPreferences.authLogin(status,email)
        }
    }


}
class ProductListViewModelFactory(
    private val repository: ProductRepository,
    private val userPreferencesRepository: UserPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


