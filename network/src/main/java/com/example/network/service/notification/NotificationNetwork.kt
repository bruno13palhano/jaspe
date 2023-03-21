package com.example.network.service.notification

import com.bruno13palhano.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationNetwork {
    fun getOfferNotificationStream(): Flow<Notification>
}