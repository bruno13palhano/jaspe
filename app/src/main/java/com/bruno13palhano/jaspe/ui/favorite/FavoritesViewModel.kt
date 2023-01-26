package com.bruno13palhano.jaspe.ui.favorite

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.external.FavoriteProductRepository
import kotlinx.coroutines.flow.Flow

class FavoritesViewModel(
    private val favoriteRepository: FavoriteProductRepository
) : ViewModel(){
    fun getAllFavorites(): Flow<List<FavoriteProduct>> {
        return favoriteRepository.getAllFavoriteProducts()
    }

    suspend fun deleteProductByUrlLink(favoriteProductUrlLink: String) {
        favoriteRepository.deleteFavoriteProductByUrlLink(favoriteProductUrlLink)
    }

    suspend fun deleteAllProduct(favoriteProductList: List<FavoriteProduct>) {
        favoriteRepository.deleteAllFavoriteProduct(favoriteProductList)
    }

    fun getAllFavoritesVisible(): Flow<List<FavoriteProduct>> {
        return favoriteRepository.getAllFavoriteProductsVisible()
    }
}