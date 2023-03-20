package com.bruno13palhano.repository.repository.notification

import com.bruno13palhano.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun insertNotification(notification: Notification)
    suspend fun deleteNotification(notification: Notification)
    suspend fun deleteAllNotifications(notifications: List<Notification>)
    fun getAllNotificationsStream(): Flow<List<Notification>>
    suspend fun setNotificationVisualized(id: Long, isVisualized: Boolean)
}