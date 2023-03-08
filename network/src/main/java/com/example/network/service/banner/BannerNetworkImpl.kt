package com.example.network.service.banner

import com.bruno13palhano.model.Banner
import com.example.network.model.asBanner
import com.example.network.service.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BannerNetworkImpl @Inject constructor(
    private val apiService: Service
) : BannerNetwork {
    override suspend fun getBanners(): Flow<List<Banner>> = flow {
        try {
            emit(apiService.getBanners().map {
                it.asBanner()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getBannerById(bannerId: Long): Flow<Banner> = flow {
        try {
            emit(apiService.getBannerById(bannerId).asBanner())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAmazonBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getAmazonBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getNaturaBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getNaturaBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAvonBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                apiService.getAvonBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}