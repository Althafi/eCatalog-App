package com.example.mycatalog.data.preferences

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.mycatalog.data.model.Profile
import com.example.mycatalog.data.preferences.PreferencesKeys.EMAIL
import com.example.mycatalog.data.preferences.PreferencesKeys.FULL_NAME
import com.example.mycatalog.data.preferences.PreferencesKeys.GENDER
import com.example.mycatalog.data.preferences.PreferencesKeys.IMG_PROFILE
import com.example.mycatalog.data.preferences.PreferencesKeys.LOGIN
import com.example.mycatalog.data.preferences.PreferencesKeys.PHONE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//key untuk memperoleh dan mengupdate dataStore
object PreferencesKeys{
        val LOGIN = booleanPreferencesKey("LOGIN_STATUS")
        val EMAIL = stringPreferencesKey("EMAIL")
        val FULL_NAME = stringPreferencesKey("FULL_NAME")
        val GENDER = stringPreferencesKey("GENDER")
        val PHONE = stringPreferencesKey("PHONE")
        val IMG_PROFILE = stringPreferencesKey("IMG_PROFILE")


    }


class UserPreferences(
    private val userPreferencesStore: DataStore<Preferences>, contex: Context,
){
    val userPreferencesFlow: Flow<Profile> = userPreferencesStore.data
        .catch { exception ->
            // untuk menampilkan error selama datastore membaca data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // mengambil nilai dari profile, dengan awalan tidak di set:
            val login = preferences[LOGIN] ?: false
            val email = preferences[EMAIL] ?: ""
            val fullName = preferences[FULL_NAME] ?: ""
            val gender = preferences[GENDER] ?: ""
            val phone = preferences[PHONE] ?: ""
            val imgProfile = preferences[IMG_PROFILE]?.let{
                Log.d("TEST", it)
                Uri.parse(it)
            }
            Profile(login, email, fullName, gender, phone, imgProfile)


        }



    // untuk memungkinkan memperbarui properti yang ada di dalam fungsi authLogin
    suspend fun authLogin(statusLogin: Boolean, email: String){
        //untuk menulis data
        userPreferencesStore.edit {preferences ->
            preferences[LOGIN] = statusLogin
            preferences[EMAIL] = email
        }
    }

    // untuk memungkinkan memperbarui properti yang ada di dalam fungsi userProfile
    suspend fun userProfile(fName: String, gender: String, phone: String){
        //untuk menulis data
        userPreferencesStore.edit {preferences ->
            preferences[FULL_NAME] = fName
            preferences[GENDER] = gender
            preferences[PHONE] = phone

        }
    }

    // untuk memungkinkan memperbarui properti yang ada di dalam fungsi setImage
    suspend fun setImage(img: Uri?){
        //untuk menulis data
        userPreferencesStore.edit { preferences ->
            preferences[IMG_PROFILE] = img.toString()
        }
    }





}

