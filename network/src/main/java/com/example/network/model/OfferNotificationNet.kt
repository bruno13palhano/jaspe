package com.example.network.model

import com.bruno13palhano.model.Notification

internal data class OfferNotificationNet(
    val title: String,
    val description: String
)

internal fun OfferNotificationNet.asOfferNotification() = Notification(
    title = title,
    description = description
)
