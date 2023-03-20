package com.bruno13palhano.repository.repository.banner

import com.bruno13palhano.model.Banner
import com.bruno13palhano.repository.database.dao.BannerDao
import com.bruno13palhano.repository.model.asBanner
import com.bruno13palhano.repository.model.asBannerRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BannerRepositoryImpl @Inject constructor(
    private val dao: BannerDao,
) : BannerRepository {

    override suspend fun insertBanners(bannerList: List<Banner>) {
        dao.insertAll(bannerList.map {
            it.asBannerRep()
        })
    }

    override fun getBannersByCompanyStream(
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

    override fun getLastBannerByCompanyStream(bannerCompany: String): Flow<Banner> {
        return dao.getLastBannerByCompany(bannerCompany).map {
            try {
                it.asBanner()
            } catch (ignored: Exception) {
                Banner()
            }
        }
    }
}