package com.example.mycatalog.data.model

import android.net.Uri


data class Profile(

    val loginStatus : Boolean,
    val email : String,
    val fullName : String,
    val gender : String,
    val phone : String,
    val imgProfile : Uri?

)
