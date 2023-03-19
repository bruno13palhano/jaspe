package com.bruno13palhano.jaspe.ui.search

import app.cash.turbine.test
import com.bruno13palhano.jaspe.ui.ModelFactory.makeSearchCache
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.model.SearchCache
import com.bruno13palhano.repository.repository.cache.SearchCacheRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SearchDialogViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var searchCacheRepository: SearchCacheRepository

    private lateinit var viewModel: SearchDialogViewModel

    private lateinit var searchCacheList: List<SearchCache>

    @Before
    fun setUp() = runTest {
        searchCacheList = listOf(makeSearchCache(), makeSearchCache(), makeSearchCache())

        doReturn(flow { emit(searchCacheList) }).`when`(searchCacheRepository)
            .getAllSearchCache()

        viewModel = SearchDialogViewModel(searchCacheRepository)
    }

    @Test
    fun searchCache_shouldReturnSearchesCacheInFlow() = runTest {
        viewModel.searchCache.test {
            val currentSearchCacheList = awaitItem()

            Assert.assertTrue(currentSearchCacheList.containsAll(searchCacheList))
            cancel()
        }
    }
}