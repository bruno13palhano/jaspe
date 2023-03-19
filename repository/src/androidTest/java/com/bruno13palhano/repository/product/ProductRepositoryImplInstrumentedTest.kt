package com.bruno13palhano.repository.product

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.bruno13palhano.repository.database.JaspeDatabase
import com.bruno13palhano.repository.database.dao.ProductDao
import com.bruno13palhano.repository.repository.product.ProductRepository
import com.bruno13palhano.repository.repository.product.ProductRepositoryImpl
import com.bruno13palhano.repository.product.ProductFactory.makeProduct
import com.bruno13palhano.repository.product.ProductFactory.makeRandomString
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductRepositoryImplInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var jaspeDatabase: JaspeDatabase
    private lateinit var productDao: ProductDao
    private lateinit var repository: ProductRepository

    @Before
    fun initDB() {
        jaspeDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            JaspeDatabase::class.java
        ).build()
        productDao = jaspeDatabase.productDao
        repository = ProductRepositoryImpl(productDao)
    }

    @After
    fun closeDB() {
        jaspeDatabase.close()
    }

    @Test
    fun insertProducts_shouldPersistProductsInDB() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()
        val productList = listOf(firstProduct, secondProduct)

        repository.insertProducts(productList)

        repository.getAll().test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(currentProducts))
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getAll_whenDBIsEmpty_shouldThrowException() = runBlocking {

        repository.getAll().test {
            val currentProducts = awaitItem()

            currentProducts[0].productId
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getByCompany_withInvalidType_shouldThrowException() = runBlocking {
        val product = makeProduct()
        val randomType = makeRandomString()

        repository.insertProducts(listOf(product))

        repository.getByCompany(
            randomType,
            0,
            1
        ).test {
            val currentProducts = awaitItem()

            currentProducts[0].productId
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getByCompany_withLimitEqualsZero_shouldThrowException() = runBlocking {
        val product = makeProduct()

        repository.insertProducts(listOf(product))

        repository.getByCompany(
            product.productCompany,
            0,
            0
        ).test {
            val currentProducts = awaitItem()

            currentProducts[0].productId
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getByCompany_withOffsetGreaterOrEqualsToListSize_shouldThrowException() = runBlocking {
        val product = makeProduct()

        repository.insertProducts(listOf(product))

        repository.getByCompany(
            product.productCompany,
            1,
            1
        ).test {
            val currentProducts = awaitItem()

            currentProducts[0].productName
            cancel()
        }
    }

    @Test
    fun getByCompany_shouldReturnListOfProductsInFlow() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()
        val products = listOf(firstProduct, secondProduct)

        repository.insertProducts(products)

        repository.getByCompany(
            firstProduct.productCompany,
            0,
            1
        ).test {
            val currentProducts = awaitItem()

            Assert.assertEquals(firstProduct, currentProducts[0])
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getByType_withInvalidType_shouldThrowException() = runBlocking {
        val product = makeProduct()
        val randomType = makeRandomString()

        repository.insertProducts(listOf(product))

        repository.getByType(randomType).test {
            val currentProducts = awaitItem()

            currentProducts[0].productId
            cancel()
        }
    }

    @Test
    fun getByType_shouldReturnListOfProductsInFlow() = runBlocking {
        val product = makeProduct()
        val products = listOf(product)

        repository.insertProducts(products)

        repository.getByType(product.productType).test {
            val currentProducts = awaitItem()

            currentProducts[0].productId
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun searchProduct_withInvalidName_shouldThrowException() = runBlocking {
        val product = makeProduct()
        val randomProductName = makeRandomString()

        repository.insertProducts(listOf(product))

        repository.searchProduct(randomProductName).test {
            val currentProducts = awaitItem()

            currentProducts[0].productName
            cancel()
        }
    }

    @Test
    fun searchProduct_shouldReturnListOfProductsInFLow() = runBlocking {
        val product = makeProduct()

        repository.insertProducts(listOf(product))

        repository.searchProduct(product.productName).test {
            val currentProducts = awaitItem()

            Assert.assertEquals(product, currentProducts[0])
            cancel()
        }
    }

    @Test
    fun getProductByLink_withInvalidUrl_shouldThrowException() = runBlocking {
        val product = makeProduct()
        val randomUrl = makeRandomString()

        repository.insertProducts(listOf(product))

        repository.getProductByLink(randomUrl).test {
            val error = awaitError()

            Assert.assertNotEquals(null, error.message)
            cancel()
        }
    }

    @Test
    fun getProductByLink_shouldReturnProductInFlow() = runBlocking {
        val product = makeProduct()

        repository.insertProducts(listOf(product))

        repository.getProductByLink(product.productUrlLink).test {
            val currentProduct = awaitItem()

            Assert.assertEquals(product, currentProduct)
            cancel()
        }
    }

    @Test
    fun insertLastSeenProduct_shouldPersistProductInDB() = runBlocking {
        val product = makeProduct()

        repository.insertLastSeenProduct(product)

        repository.getLastSeenProduct(product.productUrlLink).test {
            val currentProduct = awaitItem()

            Assert.assertEquals(product, currentProduct)
            cancel()
        }
    }

    @Test
    fun deleteLastSeenProduct_shouldDeleteLastSeenProductInDB() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()

        repository.insertLastSeenProduct(firstProduct)
        repository.insertLastSeenProduct(secondProduct)

        repository.deleteLastSeenByUrlLink(firstProduct.productUrlLink)

        repository.getLastSeenProduct(firstProduct.productUrlLink).test {
            val error = awaitError()

            Assert.assertNotEquals(error.message, null)
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getAllLastSeenProducts_whenIsEmpty_shouldThrowException() = runBlocking {
        repository.getAllLastSeenProducts().test {
            val currentProducts = awaitItem()

            currentProducts[0].productId
            cancel()
        }
    }

    @Test
    fun getAllLastSeenProducts_shouldReturnAListOfProductsInFLow() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()
        val products = listOf(firstProduct, secondProduct)

        repository.insertLastSeenProduct(firstProduct)
        repository.insertLastSeenProduct(secondProduct)

        repository.getAllLastSeenProducts().test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(products))
            cancel()
        }
    }

    @Test
    fun getLastSeenProduct_withInvalidUrlLink_shouldThrowException() = runBlocking {
        val product = makeProduct()
        val randomUrl = makeRandomString()

        repository.insertLastSeenProduct(product)

        repository.getLastSeenProduct(randomUrl).test {
            val error = awaitError()

            Assert.assertNotEquals(null, error.message)
            cancel()
        }
    }

    @Test
    fun getLastSeenProduct_shouldReturnProductInFlow() = runBlocking {
        val product = makeProduct()

        repository.insertLastSeenProduct(product)

        repository.getLastSeenProduct(product.productUrlLink).test {
            val currentProduct = awaitItem()

            Assert.assertEquals(product, currentProduct)
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getLastSeenProducts_withLimitEqualsZero_shouldThrowException() = runBlocking {
        val product = makeProduct()

        repository.insertLastSeenProduct(product)

        repository.getLastSeenProducts(0, 0).test {
            val currentProduct = awaitItem()

            currentProduct[0].productId
            cancel()
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getLastSeenProducts_withOffsetGreaterOrEqualsToListSize_shouldThrowException() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()

        repository.insertLastSeenProduct(firstProduct)
        repository.insertLastSeenProduct(secondProduct)

        repository.getLastSeenProducts(2,2).test {
            val currentProducts = awaitItem()

            currentProducts[0].productId
            cancel()
        }
    }
}