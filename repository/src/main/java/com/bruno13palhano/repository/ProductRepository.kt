package com.bruno13palhano.repository

import com.bruno13palhano.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    fun get(productId: Long): Flow<Product>
    fun getAll(): Flow<List<Product>>
    fun getByCompany(productCompany: String, offset: Int, limit: Int): Flow<List<Product>>
}