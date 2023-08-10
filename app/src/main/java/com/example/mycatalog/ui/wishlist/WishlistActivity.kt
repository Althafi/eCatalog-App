package com.example.mycatalog.ui.wishlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
import com.example.mycatalog.data.local.room.ProductDao
import com.example.mycatalog.data.local.room.ProductDatabase
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiConfig
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.databinding.ActivityEcatalogBinding
import com.example.mycatalog.databinding.ActivityWishlistBinding
import com.example.mycatalog.ui.detail.DetailProductActivity
import com.example.mycatalog.ui.list.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWishlistBinding
    private lateinit var viewModel: WishlistViewModel
    private val database : ProductDatabase by lazy {
        ProductDatabase.getDatabase(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ViewModelProvider(
            this,
            WishlistViewModelFactory(
                ProductRepository(
                    ApiConfig.createService(ApiService::class.java),database.productDao()),
            )
        )[WishlistViewModel::class.java].also { viewModel = it }

        // untuk mengambil product item
        val items = viewModel.itemsFavorite
        binding.bindAdapter(items)





    }
    private fun ActivityWishlistBinding.bindAdapter(items: Flow<PagingData<Product>>) {

        val adapter = ProductListAdapter(object : ProductListAdapterListener {
            override fun onClickProduct(product: Product) {
                startActivity(
                    Intent(this@WishlistActivity, DetailProductActivity::class.java)
                        .putExtra("EXTRA_ID", product.id)

                )
            }

        })
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.addItemDecoration(
            DividerItemDecoration(this@WishlistActivity, DividerItemDecoration.VERTICAL)
        )
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collect {
                    adapter.submitData(it)
                }
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}