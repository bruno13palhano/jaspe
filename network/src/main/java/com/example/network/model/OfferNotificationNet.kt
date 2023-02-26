package com.example.network.model

import com.bruno13palhano.model.Notification

internal data class OfferNotificationNet(
    val title: String,
    val description: String,
    val type: String
)

internal fun OfferNotificationNet.asOfferNotification() = Notification(
    title = title,
    description = description,
    type = type
)
