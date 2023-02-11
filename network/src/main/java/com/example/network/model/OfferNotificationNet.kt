package com.example.network.model

import com.bruno13palhano.model.OfferNotification

internal data class OfferNotificationNet(
    val title: String,
    val description: String
)

internal fun OfferNotificationNet.asOfferNotification() = OfferNotification(
    title = title,
    description = description
)
