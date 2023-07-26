package com.example.mycatalog.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mycatalog.R
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiConfig
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.databinding.ActivityEcatalogBinding
import com.example.mycatalog.ui.detail.DetailProductActivity
import com.example.mycatalog.ui.login.LoginActivity
import com.example.mycatalog.ui.profile.ProfileActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME,
    produceMigrations = {context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    })



class EcatalogActivity  : AppCompatActivity () {
    private lateinit var binding: ActivityEcatalogBinding
    private lateinit var viewModel: ProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEcatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)



        ViewModelProvider(
            this,
            ProductListViewModelFactory(
                ProductRepository(ApiConfig.createService(ApiService::class.java)), UserPreferences(dataStore, this)
            )
        )[ProductListViewModel::class.java].also { viewModel = it }


        // get products
        val items = viewModel.items
        binding.bindAdapter(items)

//        loginStatus(viewModel)

        viewModel.userPreferencesFlow.observe(this){profile ->
            if(profile.loginStatus){

            Toast.makeText(this, profile.loginStatus.toString(), Toast.LENGTH_LONG).show()

            }else{
                val intent = Intent(this, LoginActivity::class.java).apply {
                }
                startActivity(intent)
                finish()
            }

        }

    }

    private fun authLogout(viewModel: ProductListViewModel){
        viewModel.authLogin(false , "")
    }

    private fun ActivityEcatalogBinding.bindAdapter(items: Flow<PagingData<Product>>) {

        val adapter = ProductListAdapter(object : ProductListAdapterListener {
            override fun onClickProduct(product: Product) {
                startActivity(
                    Intent(this@EcatalogActivity, DetailProductActivity::class.java)
                        .putExtra("EXTRA_ID", product.id)

                )
            }

        })
        binding.rvProducts.adapter = adapter
        binding.rvProducts.addItemDecoration(
            DividerItemDecoration(this@EcatalogActivity, DividerItemDecoration.VERTICAL)
        )
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collect {
                    adapter.submitData(it)
                }
            }
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.catalog_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                //navigate to profile
                val intent = Intent(this, ProfileActivity::class.java).apply {
                }
                startActivity(intent)
                Toast.makeText(this, "Account Profile", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_logout -> {
                //navigate to login

//                val intent = Intent(this, LoginActivity::class.java).apply {
//                }
//                startActivity(intent)
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show()
                authLogout(viewModel)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}