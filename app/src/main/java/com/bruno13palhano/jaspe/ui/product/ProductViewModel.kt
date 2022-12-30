package com.bruno13palhano.jaspe.ui.product

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    fun getProduct(productId: Long): Flow<Product> {
        return productRepository.get(productId)
    }
}