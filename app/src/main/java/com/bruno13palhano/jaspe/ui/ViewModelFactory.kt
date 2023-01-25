package com.bruno13palhano.jaspe.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bruno13palhano.jaspe.ui.contact.ContactViewModel
import com.bruno13palhano.jaspe.ui.contact.ContactViewModelFactory
import com.bruno13palhano.jaspe.ui.favorite.FavoritesViewModel
import com.bruno13palhano.jaspe.ui.favorite.FavoritesViewModelFactory
import com.bruno13palhano.jaspe.ui.home.HomeViewModel
import com.bruno13palhano.jaspe.ui.home.HomeViewModelFactory
import com.bruno13palhano.jaspe.ui.product.ProductViewModel
import com.bruno13palhano.jaspe.ui.product.ProductViewModelFactory
import com.bruno13palhano.jaspe.ui.search.SearchDialogViewModel
import com.bruno13palhano.jaspe.ui.search.SearchDialogViewModelFactory
import com.bruno13palhano.jaspe.ui.search.SearchViewModel
import com.bruno13palhano.jaspe.ui.search.SearchViewModelFactory
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

    fun createSearchViewModel(): SearchViewModel {
        val searchViewModelFactory =
            SearchViewModelFactory(repositoryFactory.createProductRepository())

        return ViewModelProvider(owner, searchViewModelFactory)[SearchViewModel::class.java]
    }

    fun createSearchDialogViewModel(): SearchDialogViewModel {
        val searchDialogViewModelFactory =
            SearchDialogViewModelFactory(repositoryFactory.createSearchCacheRepository())

        return ViewModelProvider(owner, searchDialogViewModelFactory)[SearchDialogViewModel::class.java]
    }

    fun createContactViewModel(): ContactViewModel {
        val contactViewModelFactory =
            ContactViewModelFactory(repositoryFactory.createContactInfoRepository())

        return ViewModelProvider(owner, contactViewModelFactory)[ContactViewModel::class.java]
    }
}