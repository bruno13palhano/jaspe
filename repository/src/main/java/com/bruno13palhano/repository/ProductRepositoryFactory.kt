package com.bruno13palhano.repository

import android.content.Context
import com.example.network.service.ProductNetworkFactory

class ProductRepositoryFactory(
    private val context: Context
) {
    fun createProductRepository(): ProductRepository {
        val productDao = ProductDatabase.getInstance(context).productDao
        val productNetwork = ProductNetworkFactory().createProductNetWork()
        return ProductRepositoryImpl(productDao, productNetwork)
    }
}