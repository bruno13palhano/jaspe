package com.bruno13palhano.repository

import android.content.Context

class ProductRepositoryFactory(
    private val context: Context
) {
    fun createProductRepository(): ProductRepository {
        val productDao = JaspeDatabase.getInstance(context).productDao
        return ProductRepositoryImpl(productDao)
    }
    
    fun createBannerRepository(): BannerRepository {
        val bannerDao = JaspeDatabase.getInstance(context).bannerDao
        return BannerRepositoryImpl(bannerDao)
    }
}