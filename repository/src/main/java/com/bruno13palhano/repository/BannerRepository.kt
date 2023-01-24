package com.bruno13palhano.repository

import com.bruno13palhano.model.Banner
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun insertBanners(bannerList: List<Banner>)
    fun getByCompany(bannerCompany: String, offset: Int, limit: Int): Flow<List<Banner>>
}