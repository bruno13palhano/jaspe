package com.example.network.service.banner

import com.bruno13palhano.model.Banner
import com.example.network.model.asBanner
import com.example.network.service.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BannerNetworkImpl @Inject constructor(
    private val apiService: Service
) : BannerNetwork {
    override fun getBanners(): Flow<List<Banner>> = flow {
        try {
            emit(apiService.getBanners().map {
                it.asBanner()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    override fun getBannerById(bannerId: Long): Flow<Banner> = flow {
        try {
            emit(apiService.getBannerById(bannerId).asBanner())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    override fun getAmazonBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getAmazonBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    override fun getNaturaBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getNaturaBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    override fun getAvonBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getAvonBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}