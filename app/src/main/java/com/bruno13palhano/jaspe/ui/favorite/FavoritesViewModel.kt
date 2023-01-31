package com.bruno13palhano.jaspe.ui.favorite

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.external.FavoriteProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoriteRepository: FavoriteProductRepository
) : ViewModel(){
    private val _allFavoritesVisible = MutableStateFlow<List<FavoriteProduct>>(emptyList())
    val allFavoritesVisible = _allFavoritesVisible.asStateFlow()

    init {
        viewModelScope.launch {
            favoriteRepository.getAllFavoriteProductsVisible().collect {
                _allFavoritesVisible.value = it
            }
        }
    }

    suspend fun deleteProductByUrlLink(favoriteProductUrlLink: String) {
        favoriteRepository.deleteFavoriteProductByUrlLink(favoriteProductUrlLink)
    }

    suspend fun deleteAllProduct() {
        getAllFavorites().collect {
            favoriteRepository.deleteAllFavoriteProduct(it)
        }
    }

    private fun getAllFavorites(): Flow<List<FavoriteProduct>> {
        return favoriteRepository.getAllFavoriteProducts()
    }

    fun shareProduct(
        context: Context,
        productName: String,
        productUrlLink: String,
    ) {
        val shareProduct = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/*"
            putExtra(Intent.EXTRA_TITLE, productName)
            putExtra(Intent.EXTRA_TEXT, productUrlLink)
        }, null)

        context.startActivity(shareProduct)
    }
}