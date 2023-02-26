package com.bruno13palhano.model

enum class NotificationTypes(val type: String) {
    OFFERS("notification_offers"),
    NEW_PRODUCTS("notification_new_products"),
    ANNOUNCEMENT("notification_announcement"),
    DEFAULT("")
}