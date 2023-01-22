package com.bruno13palhano.repository

import com.bruno13palhano.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProduct(product: Product)
    suspend fun insertProducts(productList: List<Product>)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteProductById(productId: Long)
    fun get(productId: Long): Flow<Product>
    fun getByLink(productLink:String): Flow<Product>
    fun getAll(): Flow<List<Product>>
    fun getByCompany(productCompany: String, offset: Int, limit: Int): Flow<List<Product>>
    fun getByType(productType: String): Flow<List<Product>>
    fun searchProductByTitle(productName: String): Flow<List<Product>>
    fun getProductByLink(productUrlLink: String): Flow<Product>
}