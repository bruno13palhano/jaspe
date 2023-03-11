package com.bruno13palhano.jaspe.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.di.DefaultFavoriteProductRepository
import com.bruno13palhano.repository.repository.FavoriteProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    @DefaultFavoriteProductRepository
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
        return favoriteRepository.getAllFavoriteProducts()
    }
}