package com.bruno13palhano.repository.util

import com.bruno13palhano.model.Product
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
        productIsFavorite = productRep.productIsFavorite
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
        productIsFavorite = product.productIsFavorite
    )
}


