package com.bruno13palhano.repository

import android.content.Context

class ProductRepositoryFactory(
    private val context: Context
) {
    fun createProductRepository(): ProductRepository {
        val productDao = ProductDatabase.getInstance(context).productDao
        return ProductRepositoryImpl(productDao)
    }
}