package com.bruno13palhano.jaspe.ui.product

import android.content.Context
import android.content.Intent
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product

object ProductUtil {

    fun convertProductToFavorite(product: Product): FavoriteProduct {
        return FavoriteProduct(
            favoriteProductId = 0L,
            favoriteProductUrlImage = product.productUrlImage,
            favoriteProductName = product.productName,
            favoriteProductPrice = product.productPrice,
            favoriteProductUrlLink = product.productUrlLink,
            favoriteProductType = product.productType,
            favoriteProductDescription = product.productDescription,
            favoriteProductCompany = product.productCompany,
            favoriteProductIsVisible = true
        )
    }

    fun shareProduct(context: Context, favoriteProduct: FavoriteProduct) {
        val shareProductLink = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/*"
            putExtra(Intent.EXTRA_TEXT, favoriteProduct.favoriteProductUrlLink)
            putExtra(Intent.EXTRA_TITLE, favoriteProduct.favoriteProductName)

        }, null)
        context.startActivity(shareProductLink)
    }
}