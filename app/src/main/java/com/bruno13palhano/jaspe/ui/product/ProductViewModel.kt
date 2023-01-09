package com.bruno13palhano.jaspe.ui.product

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.FavoriteProductRepository
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.*

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository
) : ViewModel() {
    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
    }

    fun getProduct(productId: Long): Flow<Product> {
        return productRepository.get(productId)
    }

    suspend fun updateProduct(product: Product) {
        productRepository.updateProduct(product)
    }

    suspend fun addFavoriteProduct(favoriteProduct: FavoriteProduct) {
        favoriteProductRepository.insertFavoriteProduct(favoriteProduct)
    }

    fun getFavoriteProduct(favoriteProductId: Long): Flow<FavoriteProduct> {
        return favoriteProductRepository.getFavoriteProduct(favoriteProductId)
    }

    suspend fun removeFavoriteProduct(favoriteProductId: Long) {
        favoriteProductRepository.deleteFavoriteProductById(favoriteProductId)
    }
}