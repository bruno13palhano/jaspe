package com.bruno13palhano.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.bruno13palhano.repository.model.NotificationRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NotificationDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(notification: NotificationRep)

    @Delete
    suspend fun delete(notification: NotificationRep)

    @Query("SELECT * FROM notification_table")
    fun getAllNotifications(): Flow<List<NotificationRep>>
}