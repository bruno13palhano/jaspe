package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bruno13palhano.model.FavoriteProduct

@Entity(tableName = "favorite_product_table")
internal data class FavoriteProductRep(

    @ColumnInfo(name = "favorite_product_id")
    val favoriteProductId: Long,

    @ColumnInfo(name = "favorite_product_name")
    val favoriteProductName: String,

    @ColumnInfo(name = "favorite_product_url_image")
    val favoriteProductUrlImage: String,

    @ColumnInfo(name = "favorite_product_price")
    val favoriteProductPrice: Float,

    @ColumnInfo(name = "favorite_product_type")
    val favoriteProductType: String,

    @ColumnInfo(name = "favorite_product_description")
    val favoriteProductDescription: String,

    @PrimaryKey
    @ColumnInfo(name = "favorite_product_url_link")
    val favoriteProductUrlLink: String,

    @ColumnInfo(name = "favorite_product_is_visible")
    val favoriteProductIsVisible: Boolean
)

internal fun FavoriteProductRep.asFavoriteProduct() = FavoriteProduct(
    favoriteProductId = favoriteProductId,
    favoriteProductName = favoriteProductName,
    favoriteProductUrlImage = favoriteProductUrlImage,
    favoriteProductPrice = favoriteProductPrice,
    favoriteProductType = favoriteProductType,
    favoriteProductDescription = favoriteProductDescription,
    favoriteProductUrlLink = favoriteProductUrlLink,
    favoriteProductIsVisible = favoriteProductIsVisible
)

internal fun FavoriteProduct.asFavoriteProductRep() = FavoriteProductRep(
    favoriteProductId = favoriteProductId,
    favoriteProductName = favoriteProductName,
    favoriteProductUrlImage = favoriteProductUrlImage,
    favoriteProductPrice = favoriteProductPrice,
    favoriteProductType = favoriteProductType,
    favoriteProductDescription = favoriteProductDescription,
    favoriteProductUrlLink = favoriteProductUrlLink,
    favoriteProductIsVisible = favoriteProductIsVisible
)
