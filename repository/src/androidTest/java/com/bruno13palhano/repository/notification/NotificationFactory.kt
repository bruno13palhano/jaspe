package com.bruno13palhano.repository.notification

import com.bruno13palhano.model.Notification
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object NotificationFactory {

    fun makeRandomNotification() = Notification(
        id = makeRandomLong(),
        title = makeRandomString(),
        description = makeRandomString(),
        isVisualized = makeRandomBoolean(),
        type = makeRandomString()
    )

    fun makeRandomNotification(isVisualized: Boolean) = Notification(
        id = makeRandomLong(),
        title = makeRandomString(),
        description = makeRandomString(),
        isVisualized = isVisualized,
        type = makeRandomString()
    )

    fun makeRandomString() = UUID.randomUUID().toString()

    fun makeRandomLong() = ThreadLocalRandom.current()
        .nextLong(1, 1000)

    fun makeRandomBoolean() = ThreadLocalRandom.current()
        .nextBoolean()
}