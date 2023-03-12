package com.example.network.service.banner

import com.bruno13palhano.model.Banner
import kotlinx.coroutines.flow.Flow

interface BannerNetwork {
    fun getBanners(): Flow<List<Banner>>
    fun getBannerById(bannerId: Long): Flow<Banner>
    fun getAmazonBanners(): Flow<List<Banner>>
    fun getNaturaBanners(): Flow<List<Banner>>
    fun getAvonBanners(): Flow<List<Banner>>
}