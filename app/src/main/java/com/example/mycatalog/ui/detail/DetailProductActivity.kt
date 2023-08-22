package com.example.mycatalog.ui.detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.models.SlideModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.denzcoskun.imageslider.constants.ScaleTypes
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
    private lateinit var product : Product
    private val database : ProductDatabase by lazy {
        ProductDatabase.getDatabase(this)
    }
    private val viewModelDetail: DetailProductViewModel by viewModels {
        DetailProductViewModelFactory(ProductRepository(ApiConfig.createService(ApiService::class.java), database.productDao()
        ))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        val titleProduct = intent.getStringExtra("TITLE_PRODUCT")
        supportActionBar?.title = titleProduct
        val id = intent.getIntExtra("EXTRA_ID", 0 )
        viewModelDetail.getDetailProduct(id)

        viewModelDetail.detailProducts.observe(this){

            product = it
            // set image list
            val imageList = ArrayList<SlideModel>()
            product.images.forEach {url ->
                imageList.add(SlideModel(imageUrl = url))
            }
            binding.productImages.setImageList(imageList, scaleType = ScaleTypes.FIT)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
fun ImageButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}