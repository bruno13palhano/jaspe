package com.bruno13palhano.repository

data class ProductRep(
    val productId: Long,
    val productName: String,
    val productUrlImage: String,
    val productPrice: Float,
    val productType: String,
    val productDescription: String,
    val productCompany: String
)
