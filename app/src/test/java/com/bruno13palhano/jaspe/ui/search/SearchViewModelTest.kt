package com.bruno13palhano.jaspe.ui.search

import app.cash.turbine.test
import com.bruno13palhano.jaspe.ui.ModelFactory.makeProduct
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.ProductRepository
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
class SearchViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var viewModel: SearchViewModel

    private lateinit var products: List<Product>
    private var searchValue = "some product"

    @Before
    fun setUp() {
        products = listOf(makeProduct(), makeProduct(), makeProduct())

        doReturn(flow { emit(products) }).`when`(productRepository)
            .searchProduct(searchValue)

        viewModel = SearchViewModel(productRepository)
    }

    @Test
    fun searchProduct_shouldReturnProducts() = runTest {

        viewModel.searchProduct(searchValue)

        viewModel.searchProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(products))
            cancel()
        }
    }

    @Test
    fun searchProduct_withEmptySearchValue_shouldReturnEmpty() = runTest {
        searchValue = ""

        viewModel.searchProduct(searchValue)

        viewModel.searchProducts.test {
            val currentProducts = awaitItem()

            Assert.assertEquals(0, currentProducts.size)
            cancel()
        }
    }
}