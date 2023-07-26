package com.example.mycatalog.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.ui.list.ProductListViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repo: ProductRepository,
    private val userPreferences: UserPreferences
): ViewModel() {

    val userPreferencesFlow = userPreferences.userPreferencesFlow.asLiveData()

    fun authLogin(status: Boolean, email : String ){

        viewModelScope.launch {
            userPreferences.authLogin(status,email)
        }
    }


}
    class LoginViewModelFactory(
        private val repository: ProductRepository,
        private val userPreferencesRepository: UserPreferences
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository, userPreferencesRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
