package com.bruno13palhano.repository.favorite

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.database.JaspeDatabase
import com.bruno13palhano.repository.database.dao.FavoriteProductDao
import com.bruno13palhano.repository.repository.favorite.FavoriteProductRepository
import com.bruno13palhano.repository.repository.favorite.FavoriteProductRepositoryImpl
import com.bruno13palhano.repository.favorite.FavoriteFactory.createFavorite
import com.bruno13palhano.repository.favorite.FavoriteFactory.makeRandomString
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteProductRepositoryImplInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var jaspeDatabase: JaspeDatabase
    private lateinit var favoriteDao: FavoriteProductDao
    private lateinit var repository: FavoriteProductRepository

    @Before
    fun initDB() {
        jaspeDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            JaspeDatabase::class.java
        ).build()
        favoriteDao = jaspeDatabase.favoriteProductDao
        repository = FavoriteProductRepositoryImpl(favoriteDao)
    }

    @After
    fun closeDB() {
        jaspeDatabase.close()
    }

    @Test
    fun insertFavoriteProduct_shouldPersistFavoriteProductInDB() = runBlocking {
        val favoriteProduct = createFavorite()

        repository.insertFavoriteProduct(favoriteProduct)
        repository.getAllFavoriteProducts().test {
            val currentFavoriteProduct = awaitItem()[0]

            Assert.assertEquals(
                currentFavoriteProduct,
                favoriteProduct
            )
            cancel()
        }
    }

    @Test
    fun deleteAllFavoriteProducts_shouldDeleteAllFavoritesInDB() = runBlocking {
        val firstFavoriteProduct = createFavorite()
        val secondFavoriteProduct = createFavorite()
        val favoriteList = listOf(firstFavoriteProduct, secondFavoriteProduct)

        repository.insertFavoriteProduct(firstFavoriteProduct)
        repository.insertFavoriteProduct(secondFavoriteProduct)

        repository.deleteAllFavoriteProduct(favoriteList)
        repository.getAllFavoriteProducts().test {
            val favorites = awaitItem()

            Assert.assertEquals(favorites, emptyList<FavoriteProduct>())
            cancel()
        }
    }

    @Test
    fun deleteFavorite_shouldDeleteFavoriteProductInDB() = runBlocking {
        val favoriteProduct = createFavorite()

        repository.insertFavoriteProduct(favoriteProduct)
        repository.deleteFavoriteProductByUrlLink(favoriteProduct.favoriteProductUrlLink)

        repository.getAllFavoriteProducts().test {
            val favorites = awaitItem()

            Assert.assertEquals(favorites, emptyList<FavoriteProduct>())
            cancel()
        }
    }

    @Test
    fun setFavoriteProductVisibility_shouldChangeFavoriteVisibilityInDB() = runBlocking {
        val favoriteProduct = createFavorite()
        val visibility = true

        repository.insertFavoriteProduct(favoriteProduct)
        repository.setFavoriteProductVisibilityByUrl(favoriteProduct.favoriteProductUrlLink,
            visibility)

        repository.getAllFavoriteProducts().test {
            val currentFavoriteProduct = awaitItem()[0]

            Assert.assertEquals(currentFavoriteProduct.favoriteProductIsVisible,
                visibility)
            cancel()
        }
    }

    @Test
    fun getAllFavoriteProductsVisible_shouldReturnFavoriteListInFLow() = runBlocking {
        val firstFavoriteProduct = createFavorite(true)
        val secondFavoriteProduct = createFavorite(true)
        val thirdFavoriteProduct = createFavorite(false)

        repository.insertFavoriteProduct(firstFavoriteProduct)
        repository.insertFavoriteProduct(secondFavoriteProduct)
        repository.insertFavoriteProduct(thirdFavoriteProduct)

        repository.getAllFavoriteProductsVisible().test {
            val favoritesVisible = awaitItem()

            Assert.assertTrue(favoritesVisible.containsAll(listOf(
                firstFavoriteProduct, secondFavoriteProduct)))
            Assert.assertFalse(favoritesVisible.contains(thirdFavoriteProduct))
            cancel()
        }
    }

    @Test
    fun getFavoriteByUrl_withInvalidUrl_shouldReturnException() = runBlocking {
        val randomUrl = makeRandomString()
        repository.getFavoriteByLink(randomUrl).test {
            val error = awaitError()

            Assert.assertNotEquals(error.message, null)
        }
    }

    @Test
    fun getFavoriteProductByUrl_shouldReturnFavorite_WithTheSpecificUrl_InFlow() = runBlocking {
        val favoriteProduct = createFavorite()

        repository.insertFavoriteProduct(favoriteProduct)

        repository.getFavoriteByLink(favoriteProduct.favoriteProductUrlLink).test {
            val currentFavoriteProduct = awaitItem()

            Assert.assertEquals(favoriteProduct.favoriteProductUrlLink,
                currentFavoriteProduct.favoriteProductUrlLink)
            cancel()
        }
    }
}