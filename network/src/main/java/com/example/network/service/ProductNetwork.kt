package com.example.network.service

import com.bruno13palhano.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductNetwork {
    suspend fun getProducts(params: List<Int>): Flow<List<Product>>
    suspend fun getProductById(productId: Long): Flow<Product>
}