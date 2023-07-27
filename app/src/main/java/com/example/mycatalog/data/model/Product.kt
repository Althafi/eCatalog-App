package com.example.mycatalog.data.model


//Menyimpan seluruh data dari GSON API
data class Product(
    val discountPercentage: Double,
    val thumbnail: String,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val description: String,
    val id: Int,
    val title: String,
    val stock: Int,
    val category: String,
    val brand: String,
    val isFavorite: Boolean = false,
    val formattedPrice: String
)
