//package com.example.mycatalog.ui.wishlist
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.mycatalog.databinding.ActivityWishlistBinding
//import com.example.mycatalog.ui.detail.DetailProductActivity
//
//class WishlistActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityWishlistBinding
//    private val adapter by lazy {
//        WishlistAdapter { user ->
//            Intent(this, DetailProductActivity::class.java).apply {
//                putExtra("item", user)
//                startActivity(this)
//            }
//        }
//    }
//
//    private val viewModel by viewModels<WishlistViewModel> {
//        WishlistViewModel.Factory(AppModule(this))
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityWishlistBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
//        binding.rvFavorite.adapter = adapter
//
//        viewModel.getUserFavorite().observe(this) {
//            adapter.setData(it)
//        }
//    }
//}