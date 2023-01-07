package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banner_table")
internal data class BannerRep(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "banner_id")
    val bannerId: Long,

    @ColumnInfo(name = "banner_name")
    val bannerName: String,

    @ColumnInfo(name = "banner_url_image")
    val bannerUrlImage: String,

    @ColumnInfo(name = "banner_copany")
    val bannerCompany: String
)
