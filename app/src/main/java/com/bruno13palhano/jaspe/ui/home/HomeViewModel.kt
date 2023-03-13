package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bruno13palhano.authentication.core.DefaultUserFirebase
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.model.Banner
import com.bruno13palhano.model.Company
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

    val mainBanner: StateFlow<Banner> =
        bannerRepository.getLastBannerByCompany(Company.MAIN.company)
            .stateIn(
                initialValue = Banner(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val amazonBanner: StateFlow<Banner> =
        bannerRepository.getLastBannerByCompany(Company.AMAZON.company)
            .stateIn(
                initialValue = Banner(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val naturaBanner: StateFlow<Banner> =
        bannerRepository.getLastBannerByCompany(Company.NATURA.company)
            .stateIn(
                initialValue = Banner(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val avonBanner: StateFlow<Banner> =
        bannerRepository.getLastBannerByCompany(Company.AVON.company)
            .stateIn(
                initialValue = Banner(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val allProducts: StateFlow<List<Product>> = productRepository.getAll()
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val amazonProducts: StateFlow<List<Product>> =
        productRepository.getByCompany(Company.AMAZON.company, 0, 6)
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val naturaProducts: StateFlow<List<Product>> =
        productRepository.getByCompany(Company.NATURA.company, 0, 6)
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val avonProducts: StateFlow<List<Product>> =
        productRepository.getByCompany(Company.AVON.company, 0, 6)
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    val lastSeenProducts: StateFlow<List<Product>> =
        productRepository.getLastSeenProducts(0, 6)
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

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