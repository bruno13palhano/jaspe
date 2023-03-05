package com.bruno13palhano.jaspe.dependencies

import android.app.Application
import com.bruno13palhano.authentication.core.AuthenticationFactory
import com.bruno13palhano.jaspe.dependencies.factories.*
import com.bruno13palhano.repository.RepositoryFactory
import com.example.network.service.NetworkFactory

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

    private val notificationRepository = RepositoryFactory(application)
        .createNotificationRepository()

    private val userRepository = RepositoryFactory(application)
        .createUserRepository()

    private val authentication = AuthenticationFactory().createUserFirebase()

    val mainViewModelFactory = MainViewModelFactory(application, contactInfoRepository)

    val productViewModelFactory = ProductViewModelFactory(
        productRepository = productRepository,
        favoriteProductRepository = favoriteProductRepository,
        contactInfoRepository = contactInfoRepository
    )

    val searchDialogViewModelFactory = SearchDialogViewModelFactory(searchCacheRepository)

    val notificationsViewModelFactory = NotificationsViewModelFactory(notificationRepository)

    val loginViewModelFactory = LoginViewModelFactory(userRepository, authentication)
}