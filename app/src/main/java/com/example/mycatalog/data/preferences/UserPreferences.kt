package com.example.mycatalog.data.preferences

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
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
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Get our show completed value, defaulting to false if not set:
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




    suspend fun authLogin(statusLogin: Boolean, email: String){
        userPreferencesStore.edit {preferences ->
            preferences[LOGIN] = statusLogin
            preferences[EMAIL] = email
        }
    }

    suspend fun userProfile(fName: String, gender: String, phone: String){
        userPreferencesStore.edit {preferences ->
            preferences[FULL_NAME] = fName
            preferences[GENDER] = gender
            preferences[PHONE] = phone

        }
    }

    suspend fun setImage(img: Uri?){
        userPreferencesStore.edit { preferences ->
            preferences[IMG_PROFILE] = img.toString()
        }
    }

//    fun getRealPathFromUri(activity: Activity, contentUri: Uri?): String? {
//        val proj = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor: Cursor = activity.managedQuery(contentUri, proj, null, null, null)
//        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        cursor.moveToFirst()
//        return cursor.getString(column_index)
//    }




}

