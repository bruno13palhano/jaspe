package com.bruno13palhano.repository.repository

import com.bruno13palhano.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun insertNotification(notification: Notification)
    suspend fun deleteNotification(notification: Notification)
    suspend fun deleteAllNotifications(notifications: List<Notification>)
    fun getAllNotifications(): Flow<List<Notification>>
    suspend fun setNotificationVisualized(id: Long, isVisualized: Boolean)
}