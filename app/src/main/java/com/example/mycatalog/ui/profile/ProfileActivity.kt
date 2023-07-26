package com.example.mycatalog.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mycatalog.R
import com.example.mycatalog.data.network.ApiConfig
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.databinding.ActivityProfileBinding
import com.example.mycatalog.ui.form.EditProfileActivity
import com.example.mycatalog.ui.list.dataStore


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



        ViewModelProvider(
            this,
            ProfileViewModelFactory(
                ProductRepository(ApiConfig.createService(ApiService::class.java)), UserPreferences(dataStore, this)
            )
        )[ProfileViewModel::class.java].also { viewModel = it }

        viewModel.userPreferencesFlow.observe(this){profile ->
            profile.email
            profile.fullName
            profile.gender
            profile.phone
            profile.imgProfile

            binding.tvEmail.text = profile.email
            binding.tvFullName.text = profile.fullName
            binding.tvGender.text = profile.gender
            binding.tvPhone.text = profile.phone
            Glide.with(this).load(profile.imgProfile)
                .transform(CenterInside())
                .into(binding.imgProfile)


        }


        binding.btnEdit.setOnClickListener{
            val intent = Intent(this, EditProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }


}

