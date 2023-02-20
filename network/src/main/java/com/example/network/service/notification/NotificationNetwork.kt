package com.example.network.service.notification

import com.bruno13palhano.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationNetwork {
    suspend fun getOfferNotification(): Flow<Notification>
}