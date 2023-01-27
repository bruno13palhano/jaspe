package com.bruno13palhano.model

data class Product(
    val productId: Long = 0L,
    val productName: String,
    val productUrlImage: String,
    val productPrice: Float,
    val productType: String,
    val productDescription: String,
    val productCompany: String,
    val productUrlLink: String,
    val productSeen: Long
)
