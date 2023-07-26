package com.example.mycatalog.ui.detail

import androidx.lifecycle.*
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiConfig
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.repository.ProductRepository
import kotlinx.coroutines.launch

class DetailProductViewModel(private val repo: ProductRepository)
    : ViewModel() {

    private val _detailProducts = MutableLiveData<Product>()
    val detailProducts: LiveData<Product>
        get() = _detailProducts

    fun getDetailProduct(id: Int){
        viewModelScope.launch {
            val detailProducts = repo.getProduct(id)
            _detailProducts.postValue(detailProducts)
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
            ): T {
                return DetailProductViewModel(
                    ProductRepository(
                        ApiConfig.createService(ApiService::class.java)
                    )
                ) as T
            }
        }
    }
}