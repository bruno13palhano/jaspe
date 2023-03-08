package com.bruno13palhano.repository.repository

import com.bruno13palhano.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

interface FavoriteProductRepository {
    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct)
    suspend fun deleteAllFavoriteProduct(favoriteProductList: List<FavoriteProduct>)
    suspend fun deleteFavoriteProductByUrlLink(favoriteProductUrlLink: String)
    fun getFavoriteByLink(favoriteProductLink: String): Flow<FavoriteProduct>
    fun getAllFavoriteProducts(): Flow<List<FavoriteProduct>>
    fun getAllFavoriteProductsVisible(): Flow<List<FavoriteProduct>>
    suspend fun setFavoriteProductVisibilityByUrl(favoriteProductUrlLink: String, favoriteProductIsVisible: Boolean)
}