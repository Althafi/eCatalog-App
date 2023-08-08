package com.example.mycatalog.ui.detail

import androidx.lifecycle.*
import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.repository.ProductRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailProductViewModel(private val repo: ProductRepository)
    : ViewModel() {

    val resultSuccessFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()
    private val _detailProducts = MutableLiveData<Product>()
    val detailProducts: LiveData<Product>
        get() = _detailProducts

    fun getDetailProduct(id: Int){
        viewModelScope.launch {
            val detailProducts = repo.getProduct(id)
//            _detailProducts.postValue(detailProducts)
            setProduct(detailProducts)
        }
    }

    fun setProduct(product: Product){
        viewModelScope.launch {
            repo.getFavorite(product.id).collect{
                if(it == null){
                    _detailProducts.postValue(product)

                }else{
                    _detailProducts.postValue(it)
                }
            }
        }
    }

    fun setFavorite() {
        viewModelScope.launch {
            if(_detailProducts.value != null){
                if (_detailProducts.value?.isFavorite == true){
                    repo.setFavoriteDelete(_detailProducts.value!!)
               }else{
                   repo.setFavoriteInsert(_detailProducts.value!!)
               }
            }
        }
    }

//    fun findFavorite(id: Int, listenFavorite: () -> Unit) {
//        viewModelScope.launch {
//            val user = db.productDao.findById(id)
//            if (user != null) {
//                listenFavorite()
//                isFavorite = true
//            }
//        }
//    }




    //function yang dipanggil untuk menerima dan menampung data parameter dari preferences menggunakan caroutiny


}

class DetailProductViewModelFactory(
    private val repository: ProductRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}