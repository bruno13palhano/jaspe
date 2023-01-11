package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bruno13palhano.model.Banner

@Entity(tableName = "banner_table")
internal data class BannerRep(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "banner_id")
    val bannerId: Long,

    @ColumnInfo(name = "banner_name")
    val bannerName: String,

    @ColumnInfo(name = "banner_url_image")
    val bannerUrlImage: String,

    @ColumnInfo(name = "banner_company")
    val bannerCompany: String
)

internal fun BannerRep.asBanner() = Banner(
    bannerId = bannerId,
    bannerName = bannerName,
    bannerUrlImage = bannerUrlImage,
    bannerCompany = bannerCompany
)

internal fun Banner.asBannerRep() = BannerRep(
    bannerId = bannerId,
    bannerName = bannerName,
    bannerUrlImage = bannerUrlImage,
    bannerCompany = bannerCompany
)