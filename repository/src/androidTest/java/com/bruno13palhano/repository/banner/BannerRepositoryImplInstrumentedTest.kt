package com.bruno13palhano.repository.banner

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.bruno13palhano.repository.database.JaspeDatabase
import com.bruno13palhano.repository.banner.BannerFactory.createBanner
import com.bruno13palhano.repository.database.dao.BannerDao
import com.bruno13palhano.repository.repository.banner.BannerRepository
import com.bruno13palhano.repository.repository.banner.BannerRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class BannerRepositoryImplInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var jaspeDatabase: JaspeDatabase
    private lateinit var bannerDao: BannerDao
    private lateinit var repository: BannerRepository

    @Before
    fun initDB() {
        jaspeDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            JaspeDatabase::class.java
        ).build()
        bannerDao = jaspeDatabase.bannerDao
        repository = BannerRepositoryImpl(bannerDao)
    }

    @After
    fun closeDB() {
        jaspeDatabase.close()
    }

    @Test
    fun insertBanners_shouldInsertBannersInDB() = runBlocking {
        val firstBanner = createBanner()
        val secondBanner = createBanner()
        val banners = listOf(firstBanner, secondBanner)

        repository.insertBanners(banners)

        repository.getByCompany(
            firstBanner.bannerCompany,
            0,
            1
        ).test {
            val banner = awaitItem()

            Assert.assertEquals(banner[0], firstBanner)
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getBannerWithInvalidCompany_shouldThrowIndexOutOfBoundsException() = runBlocking {
        val banner = createBanner()
        repository.insertBanners(listOf(banner))
        repository.getByCompany("",0,1).test {
            val currentBanner = awaitItem()[0]

            cancel()
        }
    }

    @Test
    fun getBanner_shouldReturnBannerInFlow() = runBlocking {
        val banner = createBanner()
        repository.insertBanners(listOf(banner))
        repository.getByCompany(
            banner.bannerCompany,
            0,
            1
        ).test {
            val currentBanner = awaitItem()[0]

            Assert.assertEquals(banner, currentBanner)
            cancel()
        }
    }
}