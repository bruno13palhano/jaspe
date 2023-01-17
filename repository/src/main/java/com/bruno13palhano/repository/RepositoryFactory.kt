package com.bruno13palhano.repository

import android.content.Context

class RepositoryFactory(
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

    fun createFavoriteProductRepository(): FavoriteProductRepository {
        val favoriteRepositoryDao = JaspeDatabase.getInstance(context).favoriteProductDao
        return FavoriteProductRepositoryImpl(favoriteRepositoryDao)
    }

    fun createSearchCacheRepository(): SearchCacheRepository {
        val searchCacheDao = JaspeDatabase.getInstance(context).searchCacheDao
        return SearchCacheRepositoryImpl(searchCacheDao)
    }
}