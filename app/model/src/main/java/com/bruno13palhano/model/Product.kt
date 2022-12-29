package com.bruno13palhano.jaspe.model

data class Product(
    val productId: Long,
    val productName: String,
    val productUrlImage: String = "",
    val productPrice: Float,
    val productType: String,
    val productDescription: String = ""
)
