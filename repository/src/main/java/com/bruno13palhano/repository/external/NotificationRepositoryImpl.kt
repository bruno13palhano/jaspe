package com.bruno13palhano.repository.external

import com.bruno13palhano.model.Notification
import com.bruno13palhano.repository.dao.NotificationDao
import com.bruno13palhano.repository.model.asNotification
import com.bruno13palhano.repository.model.asNotificationRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao
) : NotificationRepository {

    override suspend fun insertNotification(notification: Notification) {
        notificationDao.insert(notification.asNotificationRep())
    }

    override suspend fun deleteNotification(notification: Notification) {
        notificationDao.delete(notification.asNotificationRep())
    }

    override suspend fun deleteAllNotifications(notifications: List<Notification>) {
        notificationDao.deleteAll(notifications.map { it.asNotificationRep() })
    }

    override fun getAllNotifications(): Flow<List<Notification>> {
        return notificationDao.getAllNotifications().map {
            it.map { notificationRep ->
                notificationRep.asNotification()
            }
        }
    }

    override suspend fun setNotificationVisualized(id: Long, isVisualized: Boolean) {
        notificationDao.setNotificationVisualized(id, isVisualized)
    }
}