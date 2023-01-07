package com.example.network.model

import com.squareup.moshi.Json

data class ProductNet(

    @Json(name = "productId")
    val productId: Long,

    @Json(name = "productName")
    val productName: String,

    @Json(name = "productUrlImage")
    val productUrlImage: String,

    @Json(name = "productPrice")
    val productPrice: Float,

    @Json(name = "productType")
    val productType: String,

    @Json(name = "productDescription")
    val productDescription: String,

    @Json(name = "productCompany")
    val productCompany: String,

    @Json(name = "productUrlLink")
    val productUrlLink: String,

    @Json(name = "productIsFavorite")
    val productIsFavorite: Boolean
)
