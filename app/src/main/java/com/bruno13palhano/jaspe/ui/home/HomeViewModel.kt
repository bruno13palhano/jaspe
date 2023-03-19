package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.authentication.core.DefaultUserFirebase
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.model.*
import com.bruno13palhano.repository.di.*
import com.bruno13palhano.repository.repository.banner.BannerRepository
import com.bruno13palhano.repository.repository.contact.ContactInfoRepository
import com.bruno13palhano.repository.repository.notification.NotificationRepository
import com.bruno13palhano.repository.repository.product.ProductRepository
import com.bruno13palhano.repository.repository.user.UserRepository
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

    val uiState = combine(
        combine(
            productRepository.getAll(),
            productRepository.getByCompany(Company.AMAZON.company,0, 6),
            productRepository.getByCompany(Company.NATURA.company, 0, 6),
            ::Triple
        ),
        combine(
            productRepository.getByCompany(Company.AVON.company, 0, 6),
            productRepository.getLastSeenProducts(0, 6),
            bannerRepository.getLastBannerByCompany(Company.MAIN.company),
            ::Triple
        ),
        combine(
            bannerRepository.getLastBannerByCompany(Company.AVON.company),
            bannerRepository.getLastBannerByCompany(Company.AMAZON.company),
            bannerRepository.getLastBannerByCompany(Company.NATURA.company),
            ::Triple
        ),
        combine(
            contactInfoRepository.getContactInfo(1L),
            userRepository.getUserByUid(authentication.getCurrentUser().uid).map {
                it.username
            },
            userRepository.getUserByUid(authentication.getCurrentUser().uid).map {
                it.urlPhoto
            },
            ::Triple
        ),
        notificationRepository.getAllNotifications().map {
            it.filterNot { notification -> notification.isVisualized }.size
        }
    ) { products, productsAndBanners, banners, contactAndUser, notificationCount ->
        HomeUiState(
            allProducts = products.first,
            amazonProducts = products.second,
            naturaProducts = products.third,
            avonProducts = productsAndBanners.first,
            lastSeenProducts = productsAndBanners.second,
            mainBanner = productsAndBanners.third,
            amazonBanner = banners.first,
            naturaBanner = banners.second,
            avonBanner = banners.third,
            contactInfo = contactAndUser.first,
            username = contactAndUser.second,
            profileUrlPhoto = contactAndUser.third,
            notificationCount = notificationCount
        )
    }
        .stateIn(
            initialValue = HomeUiState(),
            scope = viewModelScope,
            started = WhileSubscribed(5_000)
        )

    fun insertLastSeenProduct(product: Product) {
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