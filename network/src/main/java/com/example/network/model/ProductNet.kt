package com.example.network.model

import com.bruno13palhano.model.Product
import com.squareup.moshi.Json

internal data class ProductNet(

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
    val productUrlLink: String
)

internal fun ProductNet.asProduct() = Product(
    productId = productId,
    productName = productName,
    productUrlImage = productUrlImage,
    productPrice = productPrice,
    productType = productType,
    productDescription = productDescription,
    productCompany = productCompany,
    productUrlLink = productUrlLink
)

internal fun Product.asProductNet() = ProductNet(
    productId = productId,
    productName = productName,
    productUrlImage = productUrlImage,
    productPrice = productPrice,
    productType = productType,
    productDescription = productDescription,
    productCompany = productCompany,
    productUrlLink = productUrlLink
)