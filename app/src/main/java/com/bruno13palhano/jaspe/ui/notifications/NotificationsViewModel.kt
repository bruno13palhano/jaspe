package com.bruno13palhano.jaspe.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.Notification
import com.bruno13palhano.repository.di.DefaultNotificationRepository
import com.bruno13palhano.repository.repository.notification.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    @DefaultNotificationRepository
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    val notifications: StateFlow<List<Notification>> =
        notificationRepository.getAllNotifications()
            .map { notifications ->
                notifications.filterNot {
                    notification -> notification.isVisualized
                }.forEach { notification ->
                    notificationRepository
                        .setNotificationVisualized(notification.id, true)
                }
                notifications
            }
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = WhileSubscribed(5_000)
            )

    fun deleteNotification(notification: Notification) {
        viewModelScope.launch {
            notificationRepository.deleteNotification(notification)
        }
    }

    fun deleteAllNotifications(notifications: List<Notification>) {
        viewModelScope.launch {
            notificationRepository.deleteAllNotifications(notifications)
        }
    }
}