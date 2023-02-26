package com.bruno13palhano.repository.notification

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.bruno13palhano.repository.JaspeDatabase
import com.bruno13palhano.repository.dao.NotificationDao
import com.bruno13palhano.repository.external.NotificationRepository
import com.bruno13palhano.repository.external.NotificationRepositoryImpl
import com.bruno13palhano.repository.notification.NotificationFactory.makeRandomNotification
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class NotificationRepositoryImplInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var jaspeDatabase: JaspeDatabase
    private lateinit var notificationDao: NotificationDao
    private lateinit var repository: NotificationRepository

    @Before
    fun initDB() {
        jaspeDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            JaspeDatabase::class.java
        ).build()
        notificationDao = jaspeDatabase.notificationDao
        repository = NotificationRepositoryImpl(notificationDao)
    }

    @After
    fun closeDB() {
        jaspeDatabase.close()
    }

    @Test
    fun insertNotification_shouldPersistNotificationInDB() = runBlocking {
        val notification = makeRandomNotification()

        repository.insertNotification(notification)

        repository.getAllNotifications().test {
            val currentNotifications = awaitItem()

            Assert.assertTrue(currentNotifications.contains(notification))
            cancel()
        }
    }

    @Test
    fun deleteNotification_shouldDeleteNotificationInDB() = runBlocking {
        val notification = makeRandomNotification()

        repository.insertNotification(notification)
        repository.deleteNotification(notification)

        repository.getAllNotifications().test {
            val currentNotifications = awaitItem()

            Assert.assertFalse(currentNotifications.contains(notification))
            cancel()
        }
    }

    @Test
    fun setNotificationVisualized_shouldChangeNotificationInDB() = runBlocking {
        val notification = makeRandomNotification(true)
        val isVisualized = false

        repository.insertNotification(notification)
        repository.setNotificationVisualized(notification.id, isVisualized)

        repository.getAllNotifications().test {
            val currentNotifications = awaitItem()

            Assert.assertEquals(isVisualized, currentNotifications[0].isVisualized)
            cancel()
        }
    }
}