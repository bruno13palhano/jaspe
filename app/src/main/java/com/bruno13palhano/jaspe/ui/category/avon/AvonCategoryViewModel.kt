package com.bruno13palhano.jaspe.ui.category.avon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AvonCategoryViewModel(
    private val productRepository: ProductRepository
) : ViewModel(){

    suspend fun insertLastSeenProduct(product: Product) {
        viewModelScope.launch {
            productRepository.insertLastSeenProduct(product)
        }
    }

    fun getAllProducts(): Flow<List<Product>> {
        return productRepository.getByCompany("Avon", 0, 10)
    }
}