package com.example.mycatalog.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.ui.login.LoginViewModel

class ProfileViewModel(
    private val repository: ProductRepository,
    private val userPreferences: UserPreferences
):ViewModel() {
    val userPreferencesFlow = userPreferences.userPreferencesFlow.asLiveData()

}

class ProfileViewModelFactory(
    private val repository: ProductRepository,
    private val userPreferencesRepository: UserPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}