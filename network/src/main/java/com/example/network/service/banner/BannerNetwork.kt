package com.example.network.service.banner

import com.bruno13palhano.model.Banner
import kotlinx.coroutines.flow.Flow

interface BannerNetwork {
    fun getBannersStream(): Flow<List<Banner>>
    fun getBannerByIdStream(bannerId: Long): Flow<Banner>
    fun getAmazonBannersStream(): Flow<List<Banner>>
    fun getNaturaBannersStream(): Flow<List<Banner>>
    fun getAvonBannersStream(): Flow<List<Banner>>
}