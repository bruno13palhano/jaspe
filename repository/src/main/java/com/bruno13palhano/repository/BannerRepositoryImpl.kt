package com.bruno13palhano.repository

import com.bruno13palhano.model.Banner
import com.bruno13palhano.repository.dao.BannerDao
import com.bruno13palhano.repository.util.convertBannerRepToBanner
import com.bruno13palhano.repository.util.convertBannerToBannerRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BannerRepositoryImpl(
    private val dao: BannerDao
) : BannerRepository {
    override suspend fun insertBanner(banner: Banner) {
        dao.insert(convertBannerToBannerRep(banner))
    }

    override suspend fun insertBanners(bannerList: List<Banner>) {
        dao.insertAll(bannerList.map {
            convertBannerToBannerRep(it)
        })
    }

    override suspend fun updateBanner(banner: Banner) {
        dao.update(convertBannerToBannerRep(banner))
    }

    override suspend fun deleteBanner(banner: Banner) {
        dao.delete(convertBannerToBannerRep(banner))
    }

    override suspend fun deleteBannerById(bannerId: Long) {
        dao.deleteById(bannerId)
    }

    override fun get(bannerId: Long): Flow<Banner> {
        return dao.getBannerById(bannerId).map {
            convertBannerRepToBanner(it)
        }
    }

    override fun getAll(): Flow<List<Banner>> {
        return dao.getAll().map {
            it.map { bannerRep ->
                convertBannerRepToBanner(bannerRep)
            }
        }
    }

    override fun getByCompany(
        bannerCompany: String,
        offset: Int, limit: Int
    ): Flow<List<Banner>> {
        return dao.getByCompany(bannerCompany, offset, limit)
            .map {
                it.map { bannerRep ->
                    convertBannerRepToBanner(bannerRep)
                }
            }
    }
}