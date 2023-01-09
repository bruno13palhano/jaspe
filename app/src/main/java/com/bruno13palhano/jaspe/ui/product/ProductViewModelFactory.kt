package com.bruno13palhano.jaspe.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.FavoriteProductRepository
import com.bruno13palhano.repository.ProductRepository

class ProductViewModelFactory(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(productRepository, favoriteProductRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}