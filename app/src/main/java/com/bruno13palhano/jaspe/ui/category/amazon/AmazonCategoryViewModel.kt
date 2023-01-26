package com.bruno13palhano.jaspe.ui.category.amazon

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.ProductRepository
import kotlinx.coroutines.flow.Flow

class AmazonCategoryViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    fun getAllProducts(): Flow<List<Product>> {
        return productRepository.getByType("Mercado")
    }
}