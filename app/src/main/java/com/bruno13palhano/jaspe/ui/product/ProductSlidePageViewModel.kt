package com.bruno13palhano.jaspe.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.FavoriteProductRepository
import com.bruno13palhano.repository.external.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductSlidePageViewModel(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val contactInfoRepository: ContactInfoRepository
) : ViewModel() {
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun getFavoriteProductByUrlLink(favoriteProductUrlLink: String): Flow<FavoriteProduct> {
        return favoriteProductRepository.getFavoriteByLink(favoriteProductUrlLink)
    }

    fun getProductByUrlLink(productUrlLink: String): Flow<Product> {
        return productRepository.getProductByLink(productUrlLink)
    }

    fun getProductLastSeen(productUrlLink: String): Flow<Product> {
        return productRepository.getLastSeenProduct(productUrlLink)
    }

    fun setFavorite(
        favorite: Boolean,
        favoriteProduct: FavoriteProduct
    ) {
        if (favorite) {
            viewModelScope.launch {
                if (favoriteProduct.favoriteProductIsVisible) {
                    setFavoriteVisibilityByUrlLink(favoriteProduct.favoriteProductUrlLink, false)
                } else {
                    setFavoriteVisibilityByUrlLink(favoriteProduct.favoriteProductUrlLink, true)
                }
            }
        } else {
            viewModelScope.launch {
                if (isFavorite.value) {
                    deleteFavoriteProductByUrlLink(favoriteProduct.favoriteProductUrlLink)
                } else {
                    addFavoriteProduct(favoriteProduct)
                }
            }
        }
    }

    fun setFavoriteValue(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

    private suspend fun addFavoriteProduct(favoriteProduct: FavoriteProduct) {
        _isFavorite.value = true
        favoriteProductRepository.insertFavoriteProduct(favoriteProduct)
    }

    private suspend fun deleteFavoriteProductByUrlLink(favoriteProductUrlLink: String) {
        _isFavorite.value = false
        favoriteProductRepository.deleteFavoriteProductByUrlLink(favoriteProductUrlLink)
    }

    private suspend fun setFavoriteVisibilityByUrlLink(
        favoriteProductUrlLink: String,
        favoriteProductIsVisible: Boolean
    ) {
        toggleFavorite()
        favoriteProductRepository.setFavoriteProductVisibilityByUrl(
            favoriteProductUrlLink, favoriteProductIsVisible)
    }

    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
    }
}