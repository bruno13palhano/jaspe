package com.bruno13palhano.repository.repository.usercase

import com.bruno13palhano.common.di.ApplicationScope
import com.bruno13palhano.model.Banner
import com.bruno13palhano.model.Company
import com.bruno13palhano.repository.database.dao.BannerDao
import com.bruno13palhano.repository.model.asBanner
import com.bruno13palhano.repository.model.asBannerRep
import com.bruno13palhano.repository.repository.BannerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BannerRepositoryImpl @Inject constructor(
    private val dao: BannerDao,
    @ApplicationScope private val scoped: CoroutineScope
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

    override fun getLastBannerByCompany(bannerCompany: String): Flow<Banner> {
        return dao.getLastBannerByCompany(bannerCompany).map {
            try {
                it.asBanner()
            } catch (ignored: Exception) {
                Banner()
            }
        }
    }

    override val mainBanner: Flow<Banner> =
        dao.getLastBannerByCompany(Company.MAIN.company)
            .map {
                try {
                    it.asBanner()
                } catch (ignored: Exception) {
                    Banner()
                }
            }
            .stateIn(
                initialValue = Banner(),
                scope = scoped,
                started = WhileSubscribed(5000)
            )

    override val amazonBanner: Flow<Banner> =
        dao.getLastBannerByCompany(Company.AMAZON.company)
            .map {
                try {
                    it.asBanner()
                } catch (ignored: Exception) {
                    Banner()
                }
            }
            .stateIn(
                initialValue = Banner(),
                scope = scoped,
                started = WhileSubscribed(5000)
            )

    override val naturaBanner: Flow<Banner> =
        dao.getLastBannerByCompany(Company.NATURA.company)
            .map {
                try {
                    it.asBanner()
                } catch (ignored: Exception) {
                    Banner()
                }
            }
            .stateIn(
                initialValue = Banner(),
                scope = scoped,
                started = WhileSubscribed(5000)
            )

    override val avonBanner: Flow<Banner> =
        dao.getLastBannerByCompany(Company.AVON.company)
            .map {
                try {
                    it.asBanner()
                } catch (ignored: Exception) {
                    Banner()
                }
            }
            .stateIn(
                initialValue = Banner(),
                scope = scoped,
                started = WhileSubscribed(5000)
            )
}