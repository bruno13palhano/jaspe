package com.bruno13palhano.repository.repository.banner

import com.bruno13palhano.model.Banner
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun insertBanners(bannerList: List<Banner>)
    fun getBannersByCompanyStream(bannerCompany: String, offset: Int, limit: Int): Flow<List<Banner>>
    fun getLastBannerByCompanyStream(bannerCompany: String): Flow<Banner>
}