package com.bruno13palhano.jaspe.ui.favorite

import app.cash.turbine.test
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.jaspe.ui.ModelFactory.makeFavoriteProduct
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.repository.favorite.FavoriteProductRepository
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
class FavoritesViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var favoriteRepository: FavoriteProductRepository

    private lateinit var viewModel: FavoritesViewModel

    private lateinit var favoriteProducts: List<FavoriteProduct>

    @Before
    fun setUp() {
        favoriteProducts = listOf(makeFavoriteProduct(), makeFavoriteProduct())

        doReturn(flow { emit(favoriteProducts) }).`when`(favoriteRepository)
            .getAllFavoriteProductsVisible()

        viewModel = FavoritesViewModel(favoriteRepository)
    }

    @Test
    fun allFavoritesVisible_shouldReturnFavoritesInFlow() = runTest {
        viewModel.allFavoritesVisible.test {
            val currentFavorites = awaitItem()

            Assert.assertTrue(currentFavorites.containsAll(favoriteProducts))
            cancel()
        }
    }
}