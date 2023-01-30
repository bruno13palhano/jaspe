package com.bruno13palhano.jaspe.ui.product

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.FavoriteProductRepository
import com.bruno13palhano.repository.external.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val contactInfoRepository: ContactInfoRepository
) : ViewModel() {
    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()
    private val _contactWhatsApp = MutableStateFlow<String>("")
    val contactWhatsApp = _contactWhatsApp.asStateFlow()

    init {
        viewModelScope.launch {
            contactInfoRepository.getContactInfo(1L).collect {
                _contactWhatsApp.value = it.contactWhatsApp
            }
        }
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

    fun shareProduct(context: Context, favoriteProduct: FavoriteProduct) {
        val shareProductLink = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/*"
            putExtra(Intent.EXTRA_TEXT, favoriteProduct.favoriteProductUrlLink)
            putExtra(Intent.EXTRA_TITLE, favoriteProduct.favoriteProductName)

        }, null)
        context.startActivity(shareProductLink)
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