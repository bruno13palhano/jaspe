package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_product_table")
internal data class FavoriteProductRep(

    @PrimaryKey
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

    @ColumnInfo(name = "favorite_product_url_link")
    val favoriteProductUrlLink: String
)
