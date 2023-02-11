package com.example.network.service.offers

import com.bruno13palhano.model.OfferNotification
import com.example.network.model.asOfferNotification
import com.example.network.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class OfferNotificationNetworkImpl : OfferNotificationNetwork{
    override suspend fun getOfferNotification(): Flow<OfferNotification> = flow {
        try {
            emit(ApiService.ProductApi.apiService.getOfferNotification().asOfferNotification())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}