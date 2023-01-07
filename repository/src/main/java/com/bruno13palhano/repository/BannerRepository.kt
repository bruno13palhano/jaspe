package com.bruno13palhano.repository

import com.bruno13palhano.model.Banner
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun insertBanner(banner: Banner)
    suspend fun insertBanners(bannerList: List<Banner>)
    suspend fun updateBanner(banner: Banner)
    suspend fun deleteBanner(banner: Banner)
    suspend fun deleteBannerById(bannerId: Long)
    fun get(bannerId: Long): Flow<Banner>
    fun getAll(): Flow<List<Banner>>
    fun getAllBanners(): Flow<List<Banner>>
    fun getByCompany(bannerCompany: String, offset: Int, limit: Int): Flow<List<Banner>>
}