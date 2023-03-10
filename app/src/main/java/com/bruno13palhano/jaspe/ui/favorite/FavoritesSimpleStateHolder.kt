package com.bruno13palhano.jaspe.ui.favorite

import android.content.Context
import android.content.Intent

object FavoritesSimpleStateHolder {

    fun shareProduct(
        context: Context,
        productName: String,
        productUrlLink: String
    ) {
        val shareProduct = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/*"
            putExtra(Intent.EXTRA_TITLE, productName)
            putExtra(Intent.EXTRA_TEXT, productUrlLink)
        }, null)

        context.startActivity(shareProduct)
    }
}