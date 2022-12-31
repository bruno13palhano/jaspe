package com.bruno13palhano.jaspe.ui.favorite

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class FavoritesViewModel(
    private val productRepository: ProductRepository
) : ViewModel(){
    fun getAllFavorites(): Flow<List<Product>> {
        return productRepository.getAllFavorites()
    }
}