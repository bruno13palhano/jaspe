package com.example.network.service.notification

import com.bruno13palhano.model.Notification
import com.example.network.model.asOfferNotification
import com.example.network.service.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NotificationNetworkImpl @Inject constructor(
    private val apiService: Service
) : NotificationNetwork{
    override suspend fun getOfferNotification(): Flow<Notification> = flow {
        try {
            emit(apiService.getOfferNotification().asOfferNotification())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}