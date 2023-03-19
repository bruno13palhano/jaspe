package com.bruno13palhano.jaspe.ui.category.common

import app.cash.turbine.test
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.jaspe.ui.ModelFactory.makeProduct
import com.bruno13palhano.model.Company
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Route
import com.bruno13palhano.model.Type
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
class CategoriesViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var viewModel: CategoriesViewModel

    private lateinit var allProducts: List<Product>
    private val productOffset = 0
    private val productLimit = 20

    @Before
    fun setUp() {
        allProducts = listOf(makeProduct(), makeProduct(), makeProduct())

        viewModel = CategoriesViewModel(productRepository)
    }

    @Test
    fun allProducts_WithBabyRoute_shouldReturnBabyProducts() = runTest {
        allProducts = listOf(
            makeProduct(type = Type.BABY.type),
            makeProduct(type = Type.BABY.type),
            makeProduct(type = Type.BABY.type)
        )

        doReturn( flow { emit(allProducts) }).`when`(productRepository)
            .getByType(Type.BABY.type)

        viewModel.setProducts(Route.BABY.route)

        viewModel.allProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(allProducts))
            cancel()
        }
    }

    @Test
    fun allProducts_WithMarketRoute_shouldReturnAmazonProducts() = runTest {
        allProducts = listOf(
            makeProduct(type = Type.MARKET.type),
            makeProduct(type = Type.MARKET.type),
            makeProduct(type = Type.MARKET.type)
        )

        doReturn(flow { emit(allProducts) }).`when`(productRepository)
            .getByType(Type.MARKET.type)

        viewModel.setProducts(Route.MARKET.route)

        viewModel.allProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(allProducts))
            cancel()
        }
    }

    @Test
    fun allProducts_WithAvonRoute_shouldReturnAvonProducts() = runTest {
        allProducts = listOf(
            makeProduct(company = Company.AVON.company),
            makeProduct(company = Company.AVON.company),
            makeProduct(company = Company.AVON.company)
        )

        doReturn(flow { emit(allProducts) }).`when`(productRepository)
            .getByCompany(Company.AVON.company, productOffset, productLimit)

        viewModel.setProducts(Route.AVON.route)

        viewModel.allProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(allProducts))
            cancel()
        }
    }

    @Test
    fun allProducts_WithNaturaRoute_shouldReturnNaturaProducts() = runTest {
        allProducts = listOf(
            makeProduct(company = Company.NATURA.company),
            makeProduct(company = Company.NATURA.company),
            makeProduct(company = Company.NATURA.company)
        )

        doReturn(flow {emit(allProducts)} ).`when`(productRepository)
            .getByCompany(Company.NATURA.company, productOffset, productLimit)

        viewModel.setProducts(Route.NATURA.route)

        viewModel.allProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(allProducts))
            cancel()
        }
    }

    @Test
    fun allProducts_WithOffersRoute_shouldReturnAmazonProducts() = runTest {
        allProducts = listOf(
            makeProduct(company = Company.AMAZON.company),
            makeProduct(company = Company.AMAZON.company),
            makeProduct(company = Company.AMAZON.company)
        )

        doReturn(flow { emit(allProducts) }).`when`(productRepository)
            .getByCompany(Company.AMAZON.company, productOffset, productLimit)

        viewModel.setProducts(Route.OFFERS.route)

        viewModel.allProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(allProducts))
            cancel()
        }
    }

    @Test
    fun allProducts_WithLastSeenRoute_shouldReturnLastSeenProducts() = runTest {
        allProducts = listOf(
            makeProduct(), makeProduct(), makeProduct()
        )

        doReturn(flow {emit(allProducts)} ).`when`(productRepository)
            .getLastSeenProducts(productOffset, productLimit)

        viewModel.setProducts(Route.LAST_SEEN.route)

        viewModel.allProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(allProducts))
            cancel()
        }
    }
}