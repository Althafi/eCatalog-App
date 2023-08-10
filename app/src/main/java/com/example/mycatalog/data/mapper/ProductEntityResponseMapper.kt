package com.example.mycatalog.data.mapper


import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
import com.example.mycatalog.data.model.Product
import java.text.NumberFormat
import java.util.*




fun ProductFavoriteEntity.toModel() =
    Product(
        discountPercentage = this.discountPercentage,
        thumbnail = this.thumbnail,
        images = this.images.split(","),
        price = this.price,
        formattedPrice = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this.price),
        rating = (this.rating / 5 * 100),
        description = this.description,
        id = this.id,
        title = this.title,
        stock = this.stock,
        category = this.category,
        brand = this.brand,
        isFavorite = true
    )



