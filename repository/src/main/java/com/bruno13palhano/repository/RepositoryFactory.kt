package com.bruno13palhano.repository

import android.content.Context
import com.bruno13palhano.repository.external.*

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

    fun createContactInfoRepository(): ContactInfoRepository {
        val contactRepositoryDao = JaspeDatabase.getInstance(context).contactInfoDao
        return ContactInfoRepositoryImpl(contactRepositoryDao)
    }

    fun createBlogRepository(): BlogPostRepository {
        val blogPostDao = JaspeDatabase.getInstance(context).blogPostDao
        return BlogPostRepositoryImpl(blogPostDao)
    }
}