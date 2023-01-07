package com.bruno13palhano.repository.util

import com.bruno13palhano.model.Banner
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.model.BannerRep
import com.bruno13palhano.repository.model.ProductRep

internal fun convertProductRepToProduct(productRep: ProductRep): Product {
    return Product(
        productId = productRep.productId,
        productName = productRep.productName,
        productUrlImage = productRep.productUrlImage,
        productPrice = productRep.productPrice,
        productType = productRep.productType,
        productDescription = productRep.productDescription,
        productCompany = productRep.productCompany,
        productUrlLink = productRep.productUrlLink,
        productIsFavorite = productRep.productIsFavorite
    )
}

internal fun convertBannerRepToBanner(bannerRep: BannerRep): Banner {
    return Banner(
        bannerId = bannerRep.bannerId,
        bannerName = bannerRep.bannerName,
        bannerUrlImage = bannerRep.bannerUrlImage,
        bannerCompany = bannerRep.bannerCompany
    )
}

internal fun convertProductToProductRep(product: Product): ProductRep {
    return ProductRep(
        productId = product.productId,
        productName = product.productName,
        productUrlImage = product.productUrlImage,
        productPrice = product.productPrice,
        productType = product.productType,
        productDescription = product.productDescription,
        productCompany = product.productCompany,
        productUrlLink = product.productUrlLink,
        productIsFavorite = product.productIsFavorite
    )
}

internal fun convertBannerToBannerRep(banner: Banner): BannerRep {
    return BannerRep(
        bannerId = banner.bannerId,
        bannerName = banner.bannerName,
        bannerUrlImage = banner.bannerUrlImage,
        bannerCompany = banner.bannerCompany
    )
}

