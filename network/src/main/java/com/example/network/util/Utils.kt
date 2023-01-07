package com.example.network.util

import com.bruno13palhano.model.Banner
import com.bruno13palhano.model.Product
import com.example.network.model.BannerNet
import com.example.network.model.ProductNet

internal fun convertProductNetToProduct(productNet: ProductNet): Product {
    return Product(
        productId = productNet.productId,
        productName = productNet.productName,
        productUrlImage = productNet.productUrlImage,
        productPrice = productNet.productPrice,
        productType = productNet.productType,
        productDescription = productNet.productDescription,
        productCompany = productNet.productCompany,
        productUrlLink = productNet.productUrlLink,
        productIsFavorite = productNet.productIsFavorite
    )
}

internal fun convertBannerNetToBanner(bannerNet: BannerNet): Banner {
    return Banner(
        bannerId = bannerNet.bannerId,
        bannerName = bannerNet.bannerName,
        bannerUrlImage = bannerNet.bannerUrlImage,
        bannerCompany = bannerNet.bannerCompany
    )
}