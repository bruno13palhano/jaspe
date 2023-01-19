package com.bruno13palhano.jaspe.ui.favorite

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.FavoriteProductRepository
import kotlinx.coroutines.flow.Flow

class FavoritesViewModel(
    private val favoriteRepository: FavoriteProductRepository
) : ViewModel(){
    fun getAllFavorites(): Flow<List<FavoriteProduct>> {
        return favoriteRepository.getAllFavoriteProducts()
    }

    suspend fun deleteProduct(productId: Long) {
        favoriteRepository.deleteFavoriteProductById(productId)
    }

    suspend fun deleteAllProduct(favoriteProductList: List<FavoriteProduct>) {
        favoriteRepository.deleteAllFavoriteProduct(favoriteProductList)
    }
}