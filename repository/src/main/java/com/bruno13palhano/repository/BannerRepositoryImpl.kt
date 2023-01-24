package com.bruno13palhano.repository

import com.bruno13palhano.model.Banner
import com.bruno13palhano.repository.dao.BannerDao
import com.bruno13palhano.repository.model.asBanner
import com.bruno13palhano.repository.model.asBannerRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BannerRepositoryImpl(
    private val dao: BannerDao
) : BannerRepository {

    override suspend fun insertBanners(bannerList: List<Banner>) {
        dao.insertAll(bannerList.map {
            it.asBannerRep()
        })
    }

    override fun getByCompany(
        bannerCompany: String,
        offset: Int, limit: Int
    ): Flow<List<Banner>> {
        return dao.getByCompany(bannerCompany, offset, limit)
            .map {
                it.map { bannerRep ->
                    bannerRep.asBanner()
                }
            }
    }
}