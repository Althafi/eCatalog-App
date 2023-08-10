package com.example.mycatalog.ui.detail

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mycatalog.R
import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
import com.example.mycatalog.data.local.room.ProductDao
import com.example.mycatalog.data.local.room.ProductDatabase
import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ApiConfig
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.databinding.ActivityDetailProductBinding

class DetailProductActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val database : ProductDatabase by lazy {
        ProductDatabase.getDatabase(this)
    }
    private val viewModelDetail: DetailProductViewModel by viewModels {
        DetailProductViewModelFactory(ProductRepository(ApiConfig.createService(ApiService::class.java), database.productDao()
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)





        val id = intent.getIntExtra("EXTRA_ID", 0 )
        viewModelDetail.getDetailProduct(id)

        viewModelDetail.detailProducts.observe(this){


        Glide.with(this)
            .load(it.images[0])
            .placeholder(R.drawable.outline_downloading_24)
            .transform(CenterInside(), RoundedCorners(24))
            .into(binding.productImage)

        // set product name
        binding.productName.text = it.title

        // set product price
        binding.productPrice.text = it.formattedPrice

        // set product rating
        binding.productRating.rating = it.rating.toFloat()

        // set product description
        binding.productDescription.text = it.description


            if(it.isFavorite){
                binding.btnFavorite.changeIconColor(R.color.md_theme_light_primary)
            }else{
                binding.btnFavorite.changeIconColor(R.color.grey_500)
            }

        }
        binding.btnFavorite.setOnClickListener {
            viewModelDetail.setFavorite()
        }




    }
}
fun ImageButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}