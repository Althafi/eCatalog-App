package com.example.mycatalog.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mycatalog.R
import com.example.mycatalog.databinding.ActivityDetailProductBinding

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val viewModelDetail: DetailProductViewModel by viewModels { DetailProductViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

        }




    }
}