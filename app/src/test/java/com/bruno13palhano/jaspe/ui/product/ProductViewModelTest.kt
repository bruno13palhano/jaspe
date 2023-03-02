package com.bruno13palhano.jaspe.ui.product

import app.cash.turbine.test
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeContactInfo
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeFavoriteProduct
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeProduct
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.FavoriteProductRepository
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
class ProductViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var favoriteProductRepository: FavoriteProductRepository

    @Mock
    private lateinit var contactInfoRepository: ContactInfoRepository

    private lateinit var viewModel: ProductViewModel

    private lateinit var products: List<Product>
    private lateinit var lastSeenProduct: Product
    private lateinit var contactInfo: ContactInfo
    private lateinit var favoriteProduct: FavoriteProduct
    private lateinit var product: Product

    private val contactInfoId = 1L
    private val amazonType = "Amazon"

    @Before
    fun setUp() {
        products = listOf(makeProduct(), makeProduct(), makeProduct())
        lastSeenProduct = makeProduct()
        contactInfo = makeContactInfo()
        favoriteProduct = makeFavoriteProduct(true)
        product = makeProduct()

        doReturn(flow { emit(products) }).`when`(productRepository)
            .getByType(amazonType)

        doReturn(flow { emit(favoriteProduct) }).`when`(favoriteProductRepository)
            .getFavoriteByLink(favoriteProduct.favoriteProductUrlLink)

        doReturn(flow { emit(product) }).`when`(productRepository)
            .getProductByLink(product.productUrlLink)

        doReturn(flow { emit(lastSeenProduct) }).`when`(productRepository)
            .getLastSeenProduct(lastSeenProduct.productUrlLink)

        doReturn(flow { emit(contactInfo) }).`when`(contactInfoRepository)
            .getContactInfo(contactInfoId)

        viewModel = ProductViewModel(productRepository, favoriteProductRepository,
            contactInfoRepository)
    }

    @Test
    fun contactWhatsApp_shouldReturnContactWhatsAppInFlow() = runTest {
        viewModel.contactWhatsApp.test {
            val currentWhatsApp = awaitItem()

            Assert.assertEquals(contactInfo.contactWhatsApp, currentWhatsApp)
            cancel()
        }
    }

    @Test
    fun getRelatedProducts_shouldReturnProductsInFlow() = runTest {
        viewModel.getRelatedProducts(amazonType).test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(products))
            awaitComplete()
        }
    }

    @Test
    fun getFavoriteProductByUrlLink_shouldReturnFavoriteProductInFlow() = runTest {
        viewModel.getFavoriteProductByUrlLink(favoriteProduct.favoriteProductUrlLink).test {
            val currentFavoriteProduct = awaitItem()

            Assert.assertEquals(favoriteProduct, currentFavoriteProduct)
            awaitComplete()
        }
    }

    @Test
    fun getProductByUrlLink_shouldReturnProductInFlow() = runTest {
        viewModel.getProductByUrlLink(product.productUrlLink).test {
            val currentProduct = awaitItem()

            Assert.assertEquals(product, currentProduct)
            awaitComplete()
        }
    }

    @Test
    fun getProductLastSeen_shouldReturnLastSeenProductInFlow() = runTest {
        viewModel.getProductLastSeen(lastSeenProduct.productUrlLink).test {
            val currentProduct = awaitItem()

            Assert.assertEquals(lastSeenProduct, currentProduct)
            awaitComplete()
        }
    }

    @Test
    fun setFavoriteValue_shouldChangeIsFavoriteValue() = runTest {
        val isFavorite = true

        viewModel.setFavoriteValue(isFavorite)

        viewModel.isFavorite.test {
            val currentIsFavorite = awaitItem()

            Assert.assertEquals(isFavorite, currentIsFavorite)
            cancel()
        }
    }


}