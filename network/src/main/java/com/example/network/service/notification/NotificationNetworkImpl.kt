package com.example.network.service.notification

import com.bruno13palhano.model.Notification
import com.example.network.model.asOfferNotification
import com.example.network.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NotificationNetworkImpl : NotificationNetwork{
    override suspend fun getOfferNotification(): Flow<Notification> = flow {
        try {
            emit(ApiService.ProductApi.apiService.getOfferNotification().asOfferNotification())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}