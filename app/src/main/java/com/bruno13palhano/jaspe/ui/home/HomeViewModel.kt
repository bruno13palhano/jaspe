package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.model.Banner
import com.bruno13palhano.model.Company
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val bannerRepository: BannerRepository,
    private val contactInfoRepository: ContactInfoRepository,
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
    private val authentication: UserAuthentication
) : ViewModel() {

    private val _mainBanner = MutableStateFlow(Banner())
    val mainBanner: StateFlow<Banner> = _mainBanner

    private val _amazonBanner = MutableStateFlow(Banner())
    val amazonBanner: StateFlow<Banner> = _amazonBanner

    private val _naturaBanner = MutableStateFlow(Banner())
    val naturaBanner: StateFlow<Banner> = _naturaBanner

    private val _avonBanner = MutableStateFlow(Banner())
    val avonBanner: StateFlow<Banner> = _avonBanner

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts

    private val _amazonProducts = MutableStateFlow<List<Product>>(emptyList())
    val amazonProducts: StateFlow<List<Product>> = _amazonProducts

    private val _naturaProducts = MutableStateFlow<List<Product>>(emptyList())
    val naturaProducts: StateFlow<List<Product>> = _naturaProducts

    private val _avonProducts = MutableStateFlow<List<Product>>(emptyList())
    val avonProducts: StateFlow<List<Product>> = _avonProducts

    private val _contactInfo = MutableStateFlow(ContactInfo())
    val contactInfo = _contactInfo.asStateFlow()

    private val _lastSeenProducts = MutableStateFlow<List<Product>>(emptyList())
    val lastSeenProducts = _lastSeenProducts.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _profileUrlPhoto = MutableStateFlow("")
    val profileUrlPhoto = _profileUrlPhoto.asStateFlow()

    private val _notificationsCount = MutableStateFlow(0L)
    val notificationCount = _notificationsCount.asStateFlow()

    init {
        viewModelScope.launch {
            notificationRepository.getAllNotifications().collect {
                var count = 0L
                it.forEach { notification ->
                    if (!notification.isVisualized) {
                        count++
                    }
                }
                _notificationsCount.value = count
            }
        }

        viewModelScope.launch {
            try {
                userRepository.getUserByUid(authentication.getCurrentUser().uid).collect {
                    _username.value = it.username
                    _profileUrlPhoto.value = it.urlPhoto
                }
            } catch (ignored: Exception) {}
        }

        viewModelScope.launch {
            try {
                contactInfoRepository.getContactInfo(1L).collect {
                    _contactInfo.value = it
                }
            } catch (ignored: Exception) {}
        }

        viewModelScope.launch {
            bannerRepository.getByCompany(Company.MAIN.company, 0, 1).collect { banner ->
                try {
                    _mainBanner.value = banner[0]
                } catch (ignored: IndexOutOfBoundsException) {}
            }
        }

        viewModelScope.launch {
            productRepository.getAll().collect {
                _allProducts.value = it
            }
        }

        viewModelScope.launch {
            productRepository.getByCompany(Company.AMAZON.company, 0, 6).collect {
                _amazonProducts.value = it
            }
        }

        viewModelScope.launch {
            productRepository.getByCompany(Company.NATURA.company, 0, 6).collect {
                _naturaProducts.value = it
            }
        }

        viewModelScope.launch {
            productRepository.getByCompany(Company.AVON.company, 0, 6).collect {
                _avonProducts.value = it
            }
        }

        viewModelScope.launch {
            bannerRepository.getByCompany(Company.AMAZON.company, 0, 1).collect { banner ->
                try {
                    _amazonBanner.value = banner[0]
                } catch (ignored: IndexOutOfBoundsException) {}
            }
        }

        viewModelScope.launch {
            bannerRepository.getByCompany(Company.NATURA.company, 0, 1).collect { banner ->
                try {
                    _naturaBanner.value = banner[0]
                } catch (ignored: IndexOutOfBoundsException) {}
            }
        }

        viewModelScope.launch {
            bannerRepository.getByCompany(Company.NATURA.company, 0, 1).collect { banner ->
                try {
                    _avonBanner.value = banner[0]
                } catch (ignored: IndexOutOfBoundsException) {}
            }
        }

        viewModelScope.launch {
            productRepository.getLastSeenProducts(0, 6).collect {
                _lastSeenProducts.value = it
            }
        }
    }

    suspend fun insertLastSeenProduct(product: Product) {
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