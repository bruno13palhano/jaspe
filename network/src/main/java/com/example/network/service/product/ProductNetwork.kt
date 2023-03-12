package com.example.network.service.product

import com.bruno13palhano.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductNetwork {
    fun getProducts(params: List<Int>): Flow<List<Product>>
    fun getProductById(productId: Long): Flow<Product>
    fun getAmazonProducts(): Flow<List<Product>>
    fun getNaturaProducts(): Flow<List<Product>>
    fun getAvonProducts(): Flow<List<Product>>
}