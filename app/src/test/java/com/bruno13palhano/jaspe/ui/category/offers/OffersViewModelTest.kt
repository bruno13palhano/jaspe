package com.bruno13palhano.jaspe.ui.category.offers

import app.cash.turbine.test
import com.bruno13palhano.jaspe.ui.ModelFactory.makeProduct
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.model.Company
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.repository.product.ProductRepository
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
class OffersViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var viewModel: OffersViewModel

    private lateinit var allProducts: List<Product>
    private val offset = 0
    private val limit = 20

    @Before
    fun setUp() {
        allProducts = listOf(makeProduct(), makeProduct(), makeProduct())

        doReturn(flow { emit(allProducts) }).`when`(productRepository)
            .getByCompany(Company.AMAZON.company, offset, limit)

        viewModel = OffersViewModel(productRepository)
    }

    @Test
    fun allProducts_shouldReturnAmazonProducts() = runTest {
        viewModel.allProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(allProducts))
            cancel()
        }
    }
}