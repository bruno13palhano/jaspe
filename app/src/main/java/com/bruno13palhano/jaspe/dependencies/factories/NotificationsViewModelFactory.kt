package com.bruno13palhano.jaspe.dependencies.factories

import com.bruno13palhano.jaspe.ui.notifications.NotificationsViewModel
import com.bruno13palhano.repository.external.NotificationRepository

class NotificationsViewModelFactory(
    private val notificationRepository: NotificationRepository
) : Factory<NotificationsViewModel> {
    override fun create(): NotificationsViewModel {
        return NotificationsViewModel(notificationRepository)
    }
}