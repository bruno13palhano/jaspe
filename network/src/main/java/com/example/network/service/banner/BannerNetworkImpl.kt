package com.example.network.service.banner

import com.bruno13palhano.common.di.Dispatcher
import com.bruno13palhano.common.di.ShopDaniDispatchers.IO
import com.bruno13palhano.model.Banner
import com.example.network.model.asBanner
import com.example.network.service.Service
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BannerNetworkImpl @Inject constructor(
    private val apiService: Service,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher
) : BannerNetwork {

    override fun getBannersStream(): Flow<List<Banner>> = flow {
        try {
            emit(apiService.getBanners().map {
                it.asBanner()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(dispatcher)

    override fun getBannerByIdStream(bannerId: Long): Flow<Banner> = flow {
        try {
            emit(apiService.getBannerById(bannerId).asBanner())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(dispatcher)

    override fun getAmazonBannersStream(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getAmazonBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(dispatcher)

    override fun getNaturaBannersStream(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getNaturaBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(dispatcher)

    override fun getAvonBannersStream(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getAvonBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(dispatcher)
}