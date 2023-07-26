package com.example.mycatalog.ui.form

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.ui.login.LoginViewModel
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: ProductRepository,
                           private val userPreferences: UserPreferences
) : ViewModel(){
    val userPreferencesFlow = userPreferences.userPreferencesFlow.asLiveData()

    fun userProfile(fName: String, gender: String, phone: String){
        viewModelScope.launch {
            userPreferences.userProfile(fName,gender,phone)
        }
    }

    fun setImage(img: Uri?){
        viewModelScope.launch {
            userPreferences.setImage(img)
        }
    }

}

class EditProfileViewModelFactory(
    private val repository: ProductRepository,
    private val userPreferencesRepository: UserPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}