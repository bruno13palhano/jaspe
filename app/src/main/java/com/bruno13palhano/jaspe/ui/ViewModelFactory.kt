package com.bruno13palhano.jaspe.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bruno13palhano.authentication.core.AuthenticationFactory
import com.bruno13palhano.jaspe.ui.account.AccountViewModel
import com.bruno13palhano.jaspe.ui.account.AccountViewModelFactory
import com.bruno13palhano.jaspe.ui.create_account.CreateAccountViewModel
import com.bruno13palhano.jaspe.ui.create_account.CreateAccountViewModelFactory
import com.bruno13palhano.jaspe.ui.favorite.FavoritesViewModel
import com.bruno13palhano.jaspe.ui.favorite.FavoritesViewModelFactory
import com.bruno13palhano.jaspe.ui.help.HelpViewModel
import com.bruno13palhano.jaspe.ui.help.HelpViewModelFactory
import com.bruno13palhano.jaspe.ui.home.HomeViewModel
import com.bruno13palhano.jaspe.ui.home.HomeViewModelFactory
import com.bruno13palhano.jaspe.ui.login.LoginViewModel
import com.bruno13palhano.jaspe.ui.login.LoginViewModelFactory
import com.bruno13palhano.jaspe.ui.product.*
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
    private val authenticationFactory = AuthenticationFactory()

    fun createProductViewModel(): ProductViewModel {
        val productViewModelFactory =
            ProductViewModelFactory(
                repositoryFactory.createProductRepository(),
                repositoryFactory.createFavoriteProductRepository(),
                repositoryFactory.createContactInfoRepository())

        return ViewModelProvider(owner, productViewModelFactory)[ProductViewModel::class.java]
    }

    fun createHomeViewModel(): HomeViewModel {
        val homeViewModelFactory =
            HomeViewModelFactory(
                productRepository = repositoryFactory.createProductRepository(),
                bannerRepository = repositoryFactory.createBannerRepository(),
                contactInfoRepository = repositoryFactory.createContactInfoRepository(),
                userRepository = repositoryFactory.createUserRepository(),
                authentication = authenticationFactory.createUserFirebase()
            )

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

    fun createHelpViewModel(): HelpViewModel {
        val helpViewModelFactory =
            HelpViewModelFactory(repositoryFactory.createContactInfoRepository())

        return ViewModelProvider(owner, helpViewModelFactory)[HelpViewModel::class.java]
    }

    fun createMockViewModel(): MockViewModel {
        val mockViewModelFactory =
            MockViewModelFactory(
                productRepository = repositoryFactory.createProductRepository(),
                favoriteProductRepository = repositoryFactory.createFavoriteProductRepository(),
                contactInfoRepository = repositoryFactory.createContactInfoRepository()
            )

        return ViewModelProvider(owner, mockViewModelFactory)[MockViewModel::class.java]
    }

    fun createProductSlidePageViewModel(): ProductSlidePageViewModel {
        val productSlidePageViewModelFactory =
            ProductSlidePageViewModelFactory(
                productRepository = repositoryFactory.createProductRepository(),
                favoriteProductRepository = repositoryFactory.createFavoriteProductRepository(),
                contactInfoRepository = repositoryFactory.createContactInfoRepository()
            )

        return ViewModelProvider(owner, productSlidePageViewModelFactory)[ProductSlidePageViewModel::class.java]
    }

    fun createCreateAccountViewModel(): CreateAccountViewModel {
        val createAccountViewModelFactory =
            CreateAccountViewModelFactory(
                userRepository = repositoryFactory.createUserRepository(),
                authentication = authenticationFactory.createUserFirebase()
            )

        return ViewModelProvider(owner, createAccountViewModelFactory)[CreateAccountViewModel::class.java]
    }

    fun createLoginViewModel(): LoginViewModel {
        val loginViewModelFactory =
            LoginViewModelFactory(
                userRepository = repositoryFactory.createUserRepository(),
                authentication = authenticationFactory.createUserFirebase()
            )

        return ViewModelProvider(owner, loginViewModelFactory)[LoginViewModel::class.java]
    }

    fun createAccountViewModel(): AccountViewModel {
        val accountViewModelFactory =
            AccountViewModelFactory(
                authentication = authenticationFactory.createUserFirebase(),
                userRepository = repositoryFactory.createUserRepository()
            )

        return ViewModelProvider(owner, accountViewModelFactory)[AccountViewModel::class.java]
    }
}