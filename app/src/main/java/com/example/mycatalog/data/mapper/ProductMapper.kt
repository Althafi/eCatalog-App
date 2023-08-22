package com.example.mycatalog.data.mapper

import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
import com.example.mycatalog.data.model.Product
import java.text.NumberFormat
import java.util.*

//function untuk mengkonversi data class product ke data class product favorite entity
fun Product.toProductFavoriteEntity()=
    ProductFavoriteEntity(
        discountPercentage = this.discountPercentage,
        thumbnail = this.thumbnail,
        images = this.images.joinToString (","),
        price = this.price,
        formattedPrice = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this.price),
        rating = this.rating,
        description = this.description,
        id = this.id,
        title = this.title,
        stock = this.stock,
        category = this.category,
        brand = this.brand
    )