package com.bruno13palhano.repository.search

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.bruno13palhano.repository.database.JaspeDatabase
import com.bruno13palhano.repository.database.dao.SearchCacheDao
import com.bruno13palhano.repository.repository.cache.SearchCacheRepository
import com.bruno13palhano.repository.repository.cache.SearchCacheRepositoryImpl
import com.bruno13palhano.repository.search.SearchCacheFactory.makeSearchCache
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class SearchCacheRepositoryImplInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var jaspeDatabase: JaspeDatabase
    private lateinit var searchCacheDao: SearchCacheDao
    private lateinit var repository: SearchCacheRepository

    @Before
    fun initDB() {
        jaspeDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            JaspeDatabase::class.java
        ).build()
        searchCacheDao = jaspeDatabase.searchCacheDao
        repository = SearchCacheRepositoryImpl(searchCacheDao)
    }

    @After
    fun closeDB() {
        jaspeDatabase.close()
    }

    @Test
    fun insertSearchCache_shouldPersistSearchCacheInDB() = runBlocking {
        val searchCache = makeSearchCache()

        repository.insertSearchCache(searchCache)

        repository.getAllSearchCache().test {
            val currentSearchesCache = awaitItem()

            Assert.assertEquals(searchCache, currentSearchesCache[0])
            cancel()
        }
    }

    @Test
    fun deleteSearchCache_shouldDeleteSearchCacheInDB() = runBlocking {
        val firstSearchCache = makeSearchCache()
        val secondSearchCache = makeSearchCache()

        repository.insertSearchCache(firstSearchCache)
        repository.insertSearchCache(secondSearchCache)

        repository.deleteSearchCacheById(firstSearchCache.searchCacheId)

        repository.getAllSearchCache().test {
            val currentSearchesCache = awaitItem()

            Assert.assertFalse(currentSearchesCache.contains(firstSearchCache))
            cancel()
        }
    }
}