package com.bruno13palhano.repository.repository.favorite

import com.bruno13palhano.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

interface FavoriteProductRepository {
    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct)
    suspend fun deleteAllFavoriteProduct(favoriteProductList: List<FavoriteProduct>)
    suspend fun deleteFavoriteProductByUrlLink(favoriteProductUrlLink: String)
    fun getFavoriteByLinkStream(favoriteProductLink: String): Flow<FavoriteProduct>
    fun getAllFavoriteProductsStream(): Flow<List<FavoriteProduct>>
    fun getAllFavoriteProductsVisibleStream(): Flow<List<FavoriteProduct>>
    suspend fun setFavoriteProductVisibilityByUrl(favoriteProductUrlLink: String, favoriteProductIsVisible: Boolean)
}