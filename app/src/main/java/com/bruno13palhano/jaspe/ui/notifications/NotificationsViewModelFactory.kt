package com.bruno13palhano.jaspe.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.NotificationRepository

class NotificationsViewModelFactory(
    private val notificationRepository: NotificationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            return NotificationsViewModel(notificationRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}