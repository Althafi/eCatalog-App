package com.example.mycatalog.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mycatalog.data.network.ApiConfig
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.databinding.ActivityLoginBinding
import com.example.mycatalog.ui.list.EcatalogActivity
import com.example.mycatalog.ui.list.dataStore

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewModelProvider(
            this,
            LoginViewModelFactory(
                ProductRepository(ApiConfig.createService(ApiService::class.java)), UserPreferences(dataStore, this)
            )
        )[LoginViewModel::class.java].also { viewModel = it }

        viewModel.userPreferencesFlow.observe(this){profile ->
            if (profile.loginStatus){
                Toast.makeText(this, profile.loginStatus.toString(), Toast.LENGTH_LONG).show()

            }else{

                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_LONG).show()
                val intent = Intent(this, EcatalogActivity::class.java).apply {
                }
                startActivity(intent)
                finish()
            }

        }

        binding.btnLogin.setOnClickListener{


            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()


            if (email.isEmpty() ){
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_LONG).show()
            }else if (password.isEmpty()){
                Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_LONG).show()
            } else{

                authLogin()



            }
        }
    }


    private fun authLogin(){
        viewModel.authLogin(true , email = binding.etEmail.text.toString())
    }


}
