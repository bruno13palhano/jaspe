package com.example.network.service.banner

import com.bruno13palhano.model.Banner
import com.example.network.model.asBanner
import com.example.network.service.ApiService
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
internal class BannerNetworkImpl @Inject constructor(

) : BannerNetwork {
    override suspend fun getBanners(): Flow<List<Banner>> = flow {
        try {
            emit(ApiService.ProductApi.apiService.getBanners().map {
                it.asBanner()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getBannerById(bannerId: Long): Flow<Banner> = flow {
        try {
            emit(ApiService.ProductApi.apiService.getBannerById(bannerId).asBanner())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAmazonBanners(): Flow<List<Banner>> = flow {
        try {
            emit(
                ApiService.ProductApi.apiService.getAmazonBanners().map {
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
                ApiService.ProductApi.apiService.getNaturaBanners().map {
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
                ApiService.ProductApi.apiService.getAvonBanners().map {
                    it.asBanner()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}