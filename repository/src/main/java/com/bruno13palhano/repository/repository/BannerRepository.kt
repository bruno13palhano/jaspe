package com.bruno13palhano.repository.repository

import com.bruno13palhano.model.Banner
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun insertBanners(bannerList: List<Banner>)
    fun getByCompany(bannerCompany: String, offset: Int, limit: Int): Flow<List<Banner>>
    fun getLastBannerByCompany(bannerCompany: String): Flow<Banner>
    val mainBanner: Flow<Banner>
    val amazonBanner: Flow<Banner>
    val naturaBanner: Flow<Banner>
    val avonBanner: Flow<Banner>
}