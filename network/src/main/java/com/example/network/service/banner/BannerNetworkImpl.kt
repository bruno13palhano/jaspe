package com.example.network.service.banner

import com.bruno13palhano.model.Banner
import com.example.network.service.ApiService
import com.example.network.util.convertBannerNetToBanner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class BannerNetworkImpl : BannerNetwork {
    override suspend fun getBanners(): Flow<List<Banner>> = flow {
        try {
            emit(ApiService.ProductApi.apiService.getBanners().map {
                convertBannerNetToBanner(it)
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getBannerById(bannerId: Long): Flow<Banner> = flow {
        try {
            emit(
                convertBannerNetToBanner(
                    ApiService.ProductApi.apiService.getBannerById(bannerId)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAmazonBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                ApiService.ProductApi.apiService.getAmazonBanners().map {
                    convertBannerNetToBanner(it)
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getNaturaBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                ApiService.ProductApi.apiService.getNaturaBanners().map {
                    convertBannerNetToBanner(it)
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAvonBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                ApiService.ProductApi.apiService.getAvonBanners().map {
                    convertBannerNetToBanner(it)
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}