package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    suspend fun insertProduct(product: Product) {
        productRepository.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productRepository.updateProduct(product)
    }

    fun getProduct(productId: Long): Flow<Product> {
        return productRepository.get(productId)
    }

    fun getAllProducts(): Flow<List<Product>> {
        return productRepository.getAll()
    }

    fun getAmazonProducts(): Flow<List<Product>> {
        return productRepository.getByCompany("Amazon", 0, 6)
    }

    fun getNaturaProducts(): Flow<List<Product>> {
        return productRepository.getByCompany("Natura", 0, 6)
    }

    fun getAvonProducts(): Flow<List<Product>> {
        return productRepository.getByCompany("Avon", 0, 6)
    }

    suspend fun deleteProductById(productId: Long) {
        productRepository.deleteProductById(productId)
    }

    suspend fun getProductById(productId: Long): Flow<Product> {
        return productRepository.getProductById(productId)
    }

    suspend fun getAmazonProducts(params: List<Int>): Flow<List<Product>> {
        return productRepository.getNaturaProducts(params)
    }
}