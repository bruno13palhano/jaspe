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

class MockViewModel(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val contactInfoRepository: ContactInfoRepository
) : ViewModel() {

    fun getProductUrlLink(productUrlLink: String): Flow<Product> =
        productRepository.getProductByLink(productUrlLink)

    fun getRelatedProducts(type: String): Flow<List<Product>> =
        productRepository.getByType(type)

    fun getProductLastSeen(productUrlLink: String): Flow<Product> {
        return productRepository.getLastSeenProduct(productUrlLink)
    }

    fun getFavoriteProductByUrlLink(favoriteProductUrlLink: String): Flow<FavoriteProduct> {
        return favoriteProductRepository.getFavoriteByLink(favoriteProductUrlLink)
    }
}