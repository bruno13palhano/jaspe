package com.bruno13palhano.jaspe.ui.common

import com.bruno13palhano.model.Product

fun prepareLastSeenProduct(product: Product): Product {
    return Product(
        productId = 0L,
        productName = product.productName,
        productUrlImage = product.productUrlImage,
        productPrice = product.productPrice,
        productCompany = product.productCompany,
        productUrlLink = product.productUrlLink,
        productDescription = product.productDescription,
        productType = product.productType
    )
}