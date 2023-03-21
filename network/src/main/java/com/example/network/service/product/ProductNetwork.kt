package com.example.network.service.product

import com.bruno13palhano.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductNetwork {
    fun getProductsStream(params: List<Int>): Flow<List<Product>>
    fun getProductByIdStream(productId: Long): Flow<Product>
    fun getAmazonProductsStream(): Flow<List<Product>>
    fun getNaturaProductsStream(): Flow<List<Product>>
    fun getAvonProductsStream(): Flow<List<Product>>
}