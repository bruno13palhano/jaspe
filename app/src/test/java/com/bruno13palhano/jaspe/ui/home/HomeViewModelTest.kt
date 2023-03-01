package com.bruno13palhano.jaspe.ui.home

import app.cash.turbine.test
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeBanner
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeContactInfo
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeNotification
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeProduct
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeUser
import com.bruno13palhano.model.*
import com.bruno13palhano.repository.external.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var bannerRepository: BannerRepository

    @Mock
    private lateinit var contactInfoRepository: ContactInfoRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var notificationRepository: NotificationRepository

    @Mock
    private lateinit var authentication: UserAuthentication

    private lateinit var viewModel: HomeViewModel

    private lateinit var products: List<Product>
    private lateinit var amazonProducts: List<Product>
    private lateinit var avonProducts: List<Product>
    private lateinit var naturaProducts: List<Product>
    private lateinit var lastSeenProducts: List<Product>
    private lateinit var naturaBanners: List<Banner>
    private lateinit var amazonBanners: List<Banner>
    private lateinit var avonBanners: List<Banner>
    private lateinit var mainBanners: List<Banner>
    private lateinit var contactInfo: ContactInfo
    private lateinit var notifications: List<Notification>
    private lateinit var user: User

    private val amazon = Company.AMAZON.company
    private val avon = Company.AVON.company
    private val natura = Company.NATURA.company
    private val main = Company.MAIN.company
    private val offset = 0
    private val bannersLimit = 1
    private val productsLimit = 6

    @Before
    fun setUp() {
        products = listOf(makeProduct(), makeProduct())
        amazonProducts = listOf(makeProduct(amazon), makeProduct(amazon), makeProduct(amazon))
        avonProducts = listOf(makeProduct(avon), makeProduct(avon), makeProduct(avon))
        naturaProducts = listOf(makeProduct(natura), makeProduct(natura), makeProduct(natura))
        lastSeenProducts = listOf(makeProduct(), makeProduct(), makeProduct())
        naturaBanners = listOf(makeBanner(natura), makeBanner(natura), makeBanner(avon))
        amazonBanners = listOf(makeBanner(amazon), makeBanner(amazon), makeBanner(natura))
        avonBanners = listOf(makeBanner(avon), makeBanner(avon), makeBanner(amazon))
        mainBanners = listOf(makeBanner(main), makeBanner(main), makeBanner(avon))
        contactInfo = makeContactInfo()
        notifications = listOf(makeNotification(false), makeNotification(false),
            makeNotification(false))
        user = makeUser()

        doReturn(flow{ emit(products) }).`when`(productRepository).getAll()

        doReturn(flow { emit(amazonProducts) }).`when`(productRepository)
            .getByCompany(amazon, offset, productsLimit)

        doReturn(flow { emit(avonProducts) }).`when`(productRepository)
            .getByCompany(avon, offset, productsLimit)

        doReturn(flow { emit(naturaProducts) }).`when`(productRepository)
            .getByCompany(natura, offset, productsLimit)

        doReturn(flow { emit(lastSeenProducts) }).`when`(productRepository)
            .getLastSeenProducts(offset, productsLimit)

        doReturn(flow { emit(naturaBanners) })
            .`when`(bannerRepository).getByCompany(natura, offset, bannersLimit)

        doReturn(flow { emit(amazonBanners) })
            .`when`(bannerRepository).getByCompany(amazon, offset, bannersLimit)

        doReturn(flow { emit(avonBanners) })
            .`when`(bannerRepository).getByCompany(avon, offset, bannersLimit)

        doReturn(flow { emit(mainBanners) })
            .`when`(bannerRepository).getByCompany(main, offset, bannersLimit)

        doReturn(flow { emit(contactInfo) })
            .`when`(contactInfoRepository).getContactInfo(anyLong())

        doReturn(flow { emit(notifications) })
            .`when`(notificationRepository).getAllNotifications()

        doReturn(flow { emit(user) })
            .`when`(userRepository).getUserByUid(user.uid)

        doReturn(user).`when`(authentication).getCurrentUser()

        viewModel = HomeViewModel(
            productRepository,
            bannerRepository,
            contactInfoRepository,
            userRepository,
            notificationRepository,
            authentication
        )
    }

    @Test
    fun allProducts_shouldReturnProductsInFlow() = runTest {
        viewModel.allProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(products))
            cancel()
        }
    }

    @Test
    fun amazonProducts_shouldReturnAmazonProducts() = runTest {
        viewModel.amazonProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(amazonProducts))
            cancel()
        }
    }

    @Test
    fun avonProducts_shouldReturnAvonProducts() = runTest {
        viewModel.avonProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(avonProducts))
            cancel()
        }
    }

    @Test
    fun naturaProducts_shouldReturnNaturaProducts() = runTest {
        viewModel.naturaProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(naturaProducts))
            cancel()
        }
    }

    @Test
    fun lastSeenProducts_shouldReturnProducts() = runTest {
        viewModel.lastSeenProducts.test {
            val currentProducts = awaitItem()

            Assert.assertTrue(currentProducts.containsAll(lastSeenProducts))
            cancel()
        }
    }

    @Test
    fun amazonBanner_shouldReturnBannerInFlow() = runTest {
        viewModel.amazonBanner.test {
            val currentBanner = awaitItem()

            Assert.assertTrue(amazonBanners.contains(currentBanner))
            cancel()
        }
    }

    @Test
    fun naturaBanner_shouldReturnBannerInFlow() = runTest {
        viewModel.naturaBanner.test {
            val currentBanner = awaitItem()

            Assert.assertTrue(naturaBanners.contains(currentBanner))
            cancel()
        }
    }

    @Test
    fun avonBanner_shouldReturnBannerInFlow() = runTest {
        viewModel.avonBanner.test {
            val currentBanner = awaitItem()

            Assert.assertTrue(avonBanners.contains(currentBanner))
            cancel()
        }
    }

    @Test
    fun mainBanner_shouldReturnBannerInFlow() = runTest {
        viewModel.mainBanner.test {
            val currentBanner = awaitItem()

            Assert.assertTrue(mainBanners.contains(currentBanner))
            cancel()
        }
    }

    @Test
    fun contactInfo_shouldReturnContactInfoInFlow() = runTest {
        viewModel.contactInfo.test {
            val currentContact = awaitItem()

            Assert.assertEquals(contactInfo, currentContact)
            cancel()
        }
    }

    @Test
    fun notificationCount_shouldReturnNonVisualizedNotificationsCount() = runTest {
        viewModel.notificationCount.test {
            val currentCount = awaitItem()

            Assert.assertEquals(notifications.size, currentCount.toInt())
            cancel()
        }
    }

    @Test
    fun username_ShouldReturnUsername() = runTest {
        viewModel.username.test {
            val currentUsername = awaitItem()

            Assert.assertEquals(user.username, currentUsername)
            cancel()
        }
    }

    @Test
    fun userUrlPhoto_shouldReturnUrlPhoto() = runTest {
        viewModel.profileUrlPhoto.test {
            val currentUserUrlPhoto = awaitItem()

            Assert.assertEquals(user.urlPhoto, currentUserUrlPhoto)
            cancel()
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}