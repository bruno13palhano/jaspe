package com.bruno13palhano.jaspe.ui.category.avon

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class AvonCategoryViewModel(
    private val productRepository: ProductRepository
) : ViewModel(){

    fun getAllProducts(): Flow<List<Product>> {
        return productRepository.getByCompany("Avon", 0, 10)
    }
}