package com.example.network.service

import com.example.network.model.BannerNet
import com.example.network.model.ProductNet
import retrofit2.http.GET
import retrofit2.http.Query

internal interface Service {

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

    @GET("banner")
    suspend fun getBannerById(
        @Query(value = "bannerId")bannerId: Long
    ): BannerNet

    @GET("banners")
    suspend fun getBanners(): List<BannerNet>

    @GET("banners/amazon")
    suspend fun getAmazonBanners(): List<BannerNet>

    @GET("banners/natura")
    suspend fun getNaturaBanners(): List<BannerNet>

    @GET("banners/avon")
    suspend fun getAvonBanners(): List<BannerNet>
}