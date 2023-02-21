package com.bruno13palhano.repository.external

import com.bruno13palhano.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun insertNotification(notification: Notification)
    suspend fun deleteNotification(notification: Notification)
    fun getAllNotifications(): Flow<List<Notification>>
    suspend fun setNotificationVisualized(id: Long, isVisualized: Boolean)
}