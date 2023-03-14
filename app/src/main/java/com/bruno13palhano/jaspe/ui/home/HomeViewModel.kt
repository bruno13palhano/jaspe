package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bruno13palhano.authentication.core.DefaultUserFirebase
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.di.*
import com.bruno13palhano.repository.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @DefaultProductRepository
    private val productRepository: ProductRepository,

    @DefaultBannerRepository
    private val bannerRepository: BannerRepository,

    @DefaultContactInfoRepository
    private val contactInfoRepository: ContactInfoRepository,

    @DefaultUserRepository
    private val userRepository: UserRepository,

    @DefaultNotificationRepository
    private val notificationRepository: NotificationRepository,

    @DefaultUserFirebase
    private val authentication: UserAuthentication
) : ViewModel() {

    val mainBanner = bannerRepository.mainBanner

    val amazonBanner = bannerRepository.amazonBanner

    val naturaBanner = bannerRepository.naturaBanner

    val avonBanner = bannerRepository.avonBanner

    val allProducts = productRepository.allProducts

    val amazonProducts = productRepository.amazonProducts.map {
        try { it.subList(0, 6) } catch (ignored: Exception) { it }
    }

    val naturaProducts = productRepository.naturaProducts.map {
        try { it.subList(0, 6) } catch (ignored: Exception) { it }
    }

    val avonProducts = productRepository.avonProducts.map {
        try { it.subList(0, 6) } catch (ignored: Exception) { it }
    }

    val lastSeenProducts = productRepository.lastSeenProducts.map {
        try { it.subList(0, 6) } catch (ignored: Exception) { it }
    }

    val contactInfo: StateFlow<ContactInfo> =
        contactInfoRepository.getContactInfo(1L)
            .stateIn(
                initialValue = ContactInfo(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val username: StateFlow<String> =
        userRepository.getUserByUid(authentication.getCurrentUser().uid)
            .map {
                it.username
            }
            .stateIn(
                initialValue = "",
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val profileUrlPhoto =
        userRepository.getUserByUid(authentication.getCurrentUser().uid)
            .map {
                it.urlPhoto
            }
            .stateIn(
                initialValue = "",
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val notificationCount: StateFlow<Long> = notificationRepository.getAllNotifications()
        .map { notifications ->
            var count = 0L
            notifications.forEach { notification ->
                if (!notification.isVisualized) {
                    count++
                }
            }
            count
        }
        .stateIn(
            initialValue = 0L,
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )

    fun onProductItemClick(navController: NavController, product: Product) {
        insertLastSeenProduct(product)
        HomeSimpleStateHolder.navigateToProduct(
            navController = navController,
            productUrlLink = product.productUrlLink,
            productType = product.productType
        )
    }

    private fun insertLastSeenProduct(product: Product) {
        val lastSeenProduct = prepareLastSeenProduct(product)
        viewModelScope.launch {
            try {
                productRepository.getLastSeenProduct(lastSeenProduct.productUrlLink).collect {
                    productRepository.deleteLastSeenByUrlLink(lastSeenProduct.productUrlLink)
                }
            } catch (ignored: Exception) {
            } finally {
                productRepository.insertLastSeenProduct(lastSeenProduct)
            }
        }
    }
}