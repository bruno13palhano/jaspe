package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bruno13palhano.model.Notification

@Entity(tableName = "notification_table")
internal data class NotificationRep(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notification_id")
    val id: Long,

    @ColumnInfo(name = "notification_title")
    val title: String,

    @ColumnInfo(name = "notification_description")
    val description: String
)

internal fun NotificationRep.asNotification() = Notification(
    id = id,
    title = title,
    description = description
)

internal fun Notification.asNotificationRep() = NotificationRep(
    id = id,
    title = title,
    description = description
)