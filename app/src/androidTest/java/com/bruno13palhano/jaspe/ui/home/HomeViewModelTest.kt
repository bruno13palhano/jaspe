package com.bruno13palhano.jaspe.ui.home

import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.repository.repository.banner.BannerRepository
import com.bruno13palhano.repository.repository.contact.ContactInfoRepository
import com.bruno13palhano.repository.repository.notification.NotificationRepository
import com.bruno13palhano.repository.repository.product.ProductRepository
import com.bruno13palhano.repository.repository.user.UserRepository
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class HomeViewModelTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var bannerRepository: BannerRepository
    private lateinit var contactInfoRepository: ContactInfoRepository
    private lateinit var userRepository: UserRepository
    private lateinit var notificationRepository: NotificationRepository
    private lateinit var authentication: UserAuthentication
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        productRepository = mock()
        bannerRepository = mock()
        contactInfoRepository = mock()
        userRepository = mock()
        notificationRepository = mock()
        authentication = mock()

    }

    @Test
    fun someTest() {

    }
}