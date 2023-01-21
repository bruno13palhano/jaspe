package com.bruno13palhano.repository

import com.bruno13palhano.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

interface FavoriteProductRepository {
    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct)
    suspend fun updateFavoriteProduct(favoriteProduct: FavoriteProduct)
    suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteProduct)
    suspend fun deleteAllFavoriteProduct(favoriteProductList: List<FavoriteProduct>)
    suspend fun deleteFavoriteProductById(favoriteProductId: Long)
    fun getFavoriteProduct(favoriteProductId: Long): Flow<FavoriteProduct>
    fun getFavoriteByLink(favoriteProductLink: String): Flow<FavoriteProduct>
    fun getAllFavoriteProducts(): Flow<List<FavoriteProduct>>
    fun getAllFavoriteProductsVisible(): Flow<List<FavoriteProduct>>
    suspend fun setFavoriteProductVisibility(favoriteProductId: Long, favoriteProductIsVisible: Boolean)
}