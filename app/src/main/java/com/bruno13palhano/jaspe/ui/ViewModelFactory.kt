package com.bruno13palhano.jaspe.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bruno13palhano.jaspe.ui.favorite.FavoritesViewModel
import com.bruno13palhano.jaspe.ui.favorite.FavoritesViewModelFactory
import com.bruno13palhano.jaspe.ui.home.HomeViewModel
import com.bruno13palhano.jaspe.ui.home.HomeViewModelFactory
import com.bruno13palhano.jaspe.ui.product.ProductViewModel
import com.bruno13palhano.jaspe.ui.product.ProductViewModelFactory
import com.bruno13palhano.repository.RepositoryFactory

class ViewModelFactory(
    context: Context,
    private val owner: ViewModelStoreOwner
) {
    private val repositoryFactory = RepositoryFactory(context)

    fun createProductViewModel(): ProductViewModel {
        val productViewModelFactory =
            ProductViewModelFactory(
                repositoryFactory.createProductRepository(),
                repositoryFactory.createFavoriteProductRepository())

        return ViewModelProvider(owner, productViewModelFactory)[ProductViewModel::class.java]
    }

    fun createHomeViewModel(): HomeViewModel {
        val homeViewModelFactory =
            HomeViewModelFactory(repositoryFactory.createProductRepository(), repositoryFactory.createBannerRepository())

        return ViewModelProvider(owner, homeViewModelFactory)[HomeViewModel::class.java]
    }

    fun createFavoritesViewModel(): FavoritesViewModel {
        val favoritesViewModelFactory =
            FavoritesViewModelFactory(repositoryFactory.createFavoriteProductRepository())

        return ViewModelProvider(owner, favoritesViewModelFactory)[FavoritesViewModel::class.java]
    }
}