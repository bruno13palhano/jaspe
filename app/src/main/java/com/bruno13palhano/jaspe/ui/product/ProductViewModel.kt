package com.bruno13palhano.jaspe.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.di.DefaultContactInfoRepository
import com.bruno13palhano.repository.di.DefaultFavoriteProductRepository
import com.bruno13palhano.repository.di.DefaultProductRepository
import com.bruno13palhano.repository.repository.contact.ContactInfoRepository
import com.bruno13palhano.repository.repository.favorite.FavoriteProductRepository
import com.bruno13palhano.repository.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    @DefaultProductRepository
    private val productRepository: ProductRepository,

    @DefaultFavoriteProductRepository
    private val favoriteProductRepository: FavoriteProductRepository,

    @DefaultContactInfoRepository
    private val contactInfoRepository: ContactInfoRepository
) : ViewModel() {
    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()

    val contactWhatsApp: StateFlow<String> =
        contactInfoRepository.getContactInfo(1L)
            .map { it.contactWhatsApp }
            .stateIn(
                initialValue = "",
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    fun getRelatedProducts(type: String): Flow<List<Product>> {
        return productRepository.getByType(type)
    }

    fun setFavorite(
        favorite: Boolean,
        favoriteProduct: FavoriteProduct,
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

    fun getFavoriteProductByUrlLink(favoriteProductUrlLink: String): Flow<FavoriteProduct> {
        return favoriteProductRepository.getFavoriteByLink(favoriteProductUrlLink)
    }

    fun getProductByUrlLink(productUrlLink: String): Flow<Product> {
        return productRepository.getProductByLink(productUrlLink)
    }

    fun getProductLastSeen(productUrlLink: String): Flow<Product> {
        return productRepository.getLastSeenProduct(productUrlLink)
    }

    private suspend fun deleteFavoriteProductByUrlLink(favoriteProductUrlLink: String) {
        _isFavorite.value = false
        favoriteProductRepository.deleteFavoriteProductByUrlLink(favoriteProductUrlLink)
    }

    private suspend fun setFavoriteVisibilityByUrlLink(
        favoriteProductUrlLink: String,
        favoriteProductIsVisible: Boolean
    ) {
        _isFavorite.value = !_isFavorite.value
        favoriteProductRepository.setFavoriteProductVisibilityByUrl(
            favoriteProductUrlLink, favoriteProductIsVisible)
    }
}