package com.example.network.model

import com.bruno13palhano.model.Notification
import com.squareup.moshi.Json

internal data class OfferNotificationNet(
    @Json(name = "title")
    val title: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "type")
    val type: String
)

internal fun OfferNotificationNet.asOfferNotification() = Notification(
    title = title,
    description = description,
    type = type
)
