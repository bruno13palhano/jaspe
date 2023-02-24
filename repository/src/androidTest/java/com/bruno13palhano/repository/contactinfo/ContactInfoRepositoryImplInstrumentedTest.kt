package com.bruno13palhano.repository.contactinfo

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.bruno13palhano.repository.JaspeDatabase
import com.bruno13palhano.repository.contactinfo.ContactInfoFactory.createContactInfo
import com.bruno13palhano.repository.dao.ContactInfoDao
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.ContactInfoRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ContactInfoRepositoryImplInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var jaspeDatabase: JaspeDatabase
    private lateinit var contactInfoDao: ContactInfoDao
    private lateinit var repository: ContactInfoRepository

    @Before
    fun initDB() {
        jaspeDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            JaspeDatabase::class.java
        ).build()
        contactInfoDao = jaspeDatabase.contactInfoDao
        repository = ContactInfoRepositoryImpl(contactInfoDao)
    }

    @After
    fun closeDB() {
        jaspeDatabase.close()
    }

    @Test
    fun insertContactInfo_shouldPersistContactInfoInDB() = runBlocking {
        val contactInfo = createContactInfo()

        repository.insertContactInfo(contactInfo)
        repository.getContactInfo(1L).test {
            val currentContactInfo = awaitItem()

            Assert.assertEquals(currentContactInfo.contactEmail, contactInfo.contactEmail)
            cancel()
        }
    }
}