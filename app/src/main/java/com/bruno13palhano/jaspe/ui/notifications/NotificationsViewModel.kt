package com.bruno13palhano.jaspe.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.Notification
import com.bruno13palhano.repository.external.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications = _notifications.asStateFlow()

    init {
        viewModelScope.launch {
            notificationRepository.getAllNotifications().collect {
                it.forEach { notification ->
                    if(!notification.isVisualized) {
                        notificationRepository
                            .setNotificationVisualized(notification.id, true)
                    }
                }
                _notifications.value = it
            }
        }
    }

    fun deleteNotification(notification: Notification) {
        viewModelScope.launch {
            notificationRepository.deleteNotification(notification)
        }
    }
}