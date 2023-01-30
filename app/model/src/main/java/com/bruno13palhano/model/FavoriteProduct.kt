package com.bruno13palhano.model

data class FavoriteProduct(
    val favoriteProductId: Long = 0L,
    val favoriteProductName: String,
    val favoriteProductUrlImage: String,
    val favoriteProductPrice: Float,
    val favoriteProductType: String,
    val favoriteProductDescription: String,
    val favoriteProductCompany: String,
    val favoriteProductUrlLink: String,
    val favoriteProductIsVisible: Boolean
)
