package com.bruno13palhano.jaspe.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.di.DefaultFavoriteProductRepository
import com.bruno13palhano.repository.repository.favorite.FavoriteProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    @DefaultFavoriteProductRepository
    private val favoriteRepository: FavoriteProductRepository
) : ViewModel(){

    val allFavoritesVisible: StateFlow<List<FavoriteProduct>> =
        favoriteRepository.getAllFavoriteProductsVisibleStream()
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    fun deleteProductByUrlLink(favoriteProductUrlLink: String) {
        viewModelScope.launch {
            favoriteRepository.deleteFavoriteProductByUrlLink(favoriteProductUrlLink)
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            getAllFavorites().collect {
                favoriteRepository.deleteAllFavoriteProduct(it)
            }
        }
    }

    private fun getAllFavorites(): Flow<List<FavoriteProduct>> {
        return favoriteRepository.getAllFavoriteProductsStream()
    }
}