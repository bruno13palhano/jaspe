package com.example.network.model

import com.bruno13palhano.model.Banner
import com.squareup.moshi.Json

internal data class BannerNet(

    @Json(name = "bannerId")
    val bannerId: Long,

    @Json(name = "bannerName")
    val bannerName: String,

    @Json(name = "bannerUrlImage")
    val bannerUrlImage: String,

    @Json(name = "bannerCompany")
    val bannerCompany: String
)

internal fun BannerNet.asBanner() = Banner(
    bannerId = bannerId,
    bannerName = bannerName,
    bannerUrlImage = bannerUrlImage,
    bannerCompany = bannerCompany
)

internal fun Banner.asBannerNet() = BannerNet(
    bannerId = bannerId,
    bannerName = bannerName,
    bannerUrlImage = bannerUrlImage,
    bannerCompany = bannerCompany
)