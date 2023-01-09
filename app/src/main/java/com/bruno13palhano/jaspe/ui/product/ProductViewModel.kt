package com.bruno13palhano.jaspe.ui.product

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.FavoriteProductRepository
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository
) : ViewModel() {

    fun getProduct(productId: Long): Flow<Product> {
        return productRepository.get(productId)
    }

    suspend fun updateProduct(product: Product) {
        productRepository.updateProduct(product)
    }

    fun getFavoriteProduct(favoriteProductId: Long): Flow<FavoriteProduct> {
        return favoriteProductRepository.getFavoriteProduct(favoriteProductId)
    }

    suspend fun removeFavoriteProduct(favoriteProductId: Long) {
        favoriteProductRepository.deleteFavoriteProductById(favoriteProductId)
    }
}