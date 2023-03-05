package com.bruno13palhano.jaspe.dependencies

import android.app.Application
import com.bruno13palhano.jaspe.dependencies.factories.MainViewModelFactory
import com.bruno13palhano.jaspe.dependencies.factories.ProductViewModelFactory
import com.bruno13palhano.jaspe.dependencies.factories.SearchDialogViewModelFactory
import com.bruno13palhano.repository.RepositoryFactory

class DependenciesContainer(
    application: Application,
) {
    private val contactInfoRepository = RepositoryFactory(application)
        .createContactInfoRepository()

    private val productRepository = RepositoryFactory(application)
        .createProductRepository()

    private val favoriteProductRepository = RepositoryFactory(application)
        .createFavoriteProductRepository()

    private val searchCacheRepository = RepositoryFactory(application)
        .createSearchCacheRepository()

    val mainViewModelFactory = MainViewModelFactory(application, contactInfoRepository)

    val productViewModelFactory = ProductViewModelFactory(
        productRepository = productRepository,
        favoriteProductRepository = favoriteProductRepository,
        contactInfoRepository = contactInfoRepository
    )

    val searchDialogViewModelFactory = SearchDialogViewModelFactory(searchCacheRepository)
}