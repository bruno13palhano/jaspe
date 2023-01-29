package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bruno13palhano.model.Product

@Entity(tableName = "last_seen_product_table")
internal data class LastSeenRep(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "last_seen_product_id")
    val productId: Long,

    @ColumnInfo(name = "last_seen_product_name")
    val productName: String,

    @ColumnInfo(name = "last_seen_product_url_image")
    val productUrlImage: String,

    @ColumnInfo(name = "last_seen_product_price")
    val productPrice: Float,

    @ColumnInfo(name = "last_seen_product_type")
    val productType: String,

    @ColumnInfo(name = "last_seen_product_description")
    val productDescription: String,

    @ColumnInfo(name = "last_seen_product_company")
    val productCompany: String,

    @ColumnInfo(name = "last_seen_product_url_link")
    val productUrlLink: String
)

internal fun LastSeenRep.asProduct() = Product(
    productId = productId,
    productName = productName,
    productUrlImage = productUrlImage,
    productPrice = productPrice,
    productType = productType,
    productDescription = productDescription,
    productCompany = productCompany,
    productUrlLink = productUrlLink,
)

internal fun Product.asLastSeenProduct() = LastSeenRep(
    productId = productId,
    productName = productName,
    productUrlImage = productUrlImage,
    productPrice = productPrice,
    productType = productType,
    productDescription = productDescription,
    productCompany = productCompany,
    productUrlLink = productUrlLink
)