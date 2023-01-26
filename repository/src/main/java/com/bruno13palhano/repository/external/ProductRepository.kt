package com.bruno13palhano.repository.external

import com.bruno13palhano.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProducts(productList: List<Product>)
    fun getAll(): Flow<List<Product>>
    fun getByCompany(productCompany: String, offset: Int, limit: Int): Flow<List<Product>>
    fun getByType(productType: String): Flow<List<Product>>
    fun searchProduct(productName: String): Flow<List<Product>>
    fun getProductByLink(productUrlLink: String): Flow<Product>
}