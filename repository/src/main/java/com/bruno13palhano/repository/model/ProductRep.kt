package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
internal data class ProductRep(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val productId: Long,

    @ColumnInfo(name = "product_name")
    val productName: String,

    @ColumnInfo(name = "product_url_image")
    val productUrlImage: String,

    @ColumnInfo(name = "product_price")
    val productPrice: Float,

    @ColumnInfo(name = "product_type")
    val productType: String,

    @ColumnInfo(name = "product_description")
    val productDescription: String,

    @ColumnInfo(name = "product_company")
    val productCompany: String,

    @ColumnInfo(name = "product_url_link")
    val productUrlLink: String
)