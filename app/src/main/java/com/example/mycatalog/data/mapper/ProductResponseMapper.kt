package com.example.mycatalog.data.mapper

import com.example.mycatalog.data.model.Product
import com.example.mycatalog.data.network.ProductsItemResponse
import com.example.mycatalog.data.network.ProductsResponse
import java.text.NumberFormat
import java.util.Locale


//untuk nge mapping dataclass dari ProductsResponse
fun ProductsResponse.toModel(): List<Product>{
    return this.products.map {
        it.toModel()
    }
}


//untuk nge mapping dataclass dari ProductsItemResponse
fun ProductsItemResponse.toModel() =
    Product(
        discountPercentage,
        thumbnail,
        images,
        price,
        rating,
        description,
        id,
        title,
        stock,
        category,
        brand,
        formattedPrice = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this.price),
    )