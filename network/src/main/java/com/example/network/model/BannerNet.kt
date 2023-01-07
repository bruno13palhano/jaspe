package com.example.network.model

import com.squareup.moshi.Json

data class BannerNet(

    @Json(name = "bannerId")
    val bannerId: Long,

    @Json(name = "bannerName")
    val bannerName: String,

    @Json(name = "bannerUrlImage")
    val bannerUrlImage: String,

    @Json(name = "bannerCompany")
    val bannerCompany: String
)
