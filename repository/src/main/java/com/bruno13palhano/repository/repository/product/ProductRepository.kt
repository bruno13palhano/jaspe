package com.bruno13palhano.repository.repository.product

import com.bruno13palhano.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProducts(productList: List<Product>)
    fun getProductsStream(): Flow<List<Product>>
    fun getProductsByCompanyStream(productCompany: String, offset: Int, limit: Int): Flow<List<Product>>
    fun getProductsByTypeStream(productType: String): Flow<List<Product>>
    fun searchProductsStream(productName: String): Flow<List<Product>>
    fun getProductByLinkStream(productUrlLink: String): Flow<Product>
    suspend fun insertLastSeenProduct(product: Product)
    suspend fun deleteLastSeenByUrlLink(productUrlLink: String)
    fun getAllLastSeenProductsStream(): Flow<List<Product>>
    fun getLastSeenProductStream(productUrlLink: String): Flow<Product>
    fun getLastSeenProductsStream(offset: Int, limit: Int): Flow<List<Product>>
}