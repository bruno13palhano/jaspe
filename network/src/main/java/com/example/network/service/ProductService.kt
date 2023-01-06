package com.example.network.service

import com.example.network.ProductNet
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ProductService {

    @GET("products")
    suspend fun getProducts(
        @Query(value = "params")params: List<Int>
    ): List<ProductNet>

    @GET("product")
    suspend fun getProductById(
        @Query(value = "productId")productId: Long
    ): ProductNet

    @GET("products/amazon")
    suspend fun getAmazonProducts(): List<ProductNet>

    @GET("products/natura")
    suspend fun getNaturaProducts(): List<ProductNet>

    @GET("products/avon")
    suspend fun getAvonProducts(): List<ProductNet>
}