package com.example.network.service.banner

import com.bruno13palhano.model.Banner
import kotlinx.coroutines.flow.Flow

interface BannerNetwork {
    suspend fun getBanners(): Flow<List<Banner>>
    suspend fun getBannerById(bannerId: Long): Flow<Banner>
    suspend fun getAmazonBanners(): Flow<List<Banner>>
    suspend fun getNaturaBanners(): Flow<List<Banner>>
    suspend fun getAvonBanners(): Flow<List<Banner>>
}