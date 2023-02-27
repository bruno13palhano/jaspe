package com.example.network

import com.example.network.JSONFactory.makeBanner
import com.example.network.JSONFactory.makeBannerJSON
import com.example.network.JSONFactory.makeBannerListJSON
import com.example.network.JSONFactory.makeBlogPost
import com.example.network.JSONFactory.makeBlogPostListJSON
import com.example.network.JSONFactory.makeContactInfo
import com.example.network.JSONFactory.makeContactInfoJSON
import com.example.network.JSONFactory.makeNotification
import com.example.network.JSONFactory.makeNotificationJSON
import com.example.network.JSONFactory.makeProduct
import com.example.network.JSONFactory.makeProductJSON
import com.example.network.JSONFactory.makeProductListJSON
import com.example.network.model.asBannerNet
import com.example.network.model.asBlogPostNet
import com.example.network.model.asContactInfo
import com.example.network.model.asProductNet
import com.example.network.service.Service
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkServiceTest {
    @get:Rule
    val mockWebServer = MockWebServer()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private val apiService: Service by lazy { retrofit.create(Service::class.java) }


    @Test
    fun getProducts_shouldReturnRandomProducts() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()
        val products = listOf(firstProduct, secondProduct)
        val offset = 0
        val limit = 100

        val productsJSON = makeProductListJSON(products)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(productsJSON)
                .setResponseCode(200))

        val productsResponse = apiService.getProducts(listOf(offset, limit))

        Assert.assertTrue(productsResponse.containsAll(products.map { it.asProductNet() }))

        Assert.assertEquals("/products?params=$offset&params=$limit",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getProductById_shouldReturnRandomProduct() = runBlocking {
        val product = makeProduct()
        val productJSON = makeProductJSON(product)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(productJSON)
                .setResponseCode(200))

        val productResponse = apiService.getProductById(product.productId)

        Assert.assertEquals(product.productId, productResponse.productId)

        Assert.assertEquals("/product?productId=${product.productId}",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getAmazonProducts_shouldReturnRandomProducts() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()
        val products = listOf(firstProduct, secondProduct)

        val productsJSON = makeProductListJSON(products)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(productsJSON)
                .setResponseCode(200))

        val productsResponse = apiService.getAmazonProducts()

        Assert.assertTrue(productsResponse.containsAll(products.map { it.asProductNet() }))

        Assert.assertEquals("/products/amazon",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getNaturaProducts_shouldReturnRandomProducts() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()
        val products = listOf(firstProduct, secondProduct)

        val productJSON = makeProductListJSON(products)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(productJSON)
                .setResponseCode(200)
        )

        val productsResponse = apiService.getNaturaProducts()

        Assert.assertTrue(productsResponse.containsAll(products.map { it.asProductNet() }))

        Assert.assertEquals("/products/natura",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getAvonProducts_shouldReturnRandomProducts() = runBlocking {
        val firstProduct = makeProduct()
        val secondProduct = makeProduct()
        val products = listOf(firstProduct, secondProduct)

        val productsJSON = makeProductListJSON(products)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(productsJSON)
                .setResponseCode(200))

        val productsResponse = apiService.getAvonProducts()

        Assert.assertTrue(productsResponse.containsAll(products.map { it.asProductNet() }))

        Assert.assertEquals("/products/avon",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getBannerById_shouldReturnRandomProduct() = runBlocking {
        val banner = makeBanner()

        val bannerJSON = makeBannerJSON(banner)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(bannerJSON)
                .setResponseCode(200))

        val bannerResponse = apiService.getBannerById(banner.bannerId)

        Assert.assertEquals(bannerResponse.bannerId, banner.bannerId)

        Assert.assertEquals("/banner?bannerId=${banner.bannerId}",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getBanners_shouldReturnRandomBanners() = runBlocking {
        val firstBanner = makeBanner()
        val secondBanner = makeBanner()
        val banners = listOf(firstBanner, secondBanner)

        val bannersJSON = makeBannerListJSON(banners)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(bannersJSON)
                .setResponseCode(200))

        val bannersResponse = apiService.getBanners()

        Assert.assertTrue(bannersResponse.containsAll(banners.map { it.asBannerNet() }))

        Assert.assertEquals("/",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getAmazonBanners_shouldReturnRandomBanners() = runBlocking {
        val firstBanner = makeBanner()
        val secondBanner = makeBanner()
        val banners = listOf(firstBanner, secondBanner)

        val bannersJSON = makeBannerListJSON(banners)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(bannersJSON)
                .setResponseCode(200))

        val bannersResponse = apiService.getAmazonBanners()

        Assert.assertTrue(bannersResponse.containsAll(banners.map { it.asBannerNet() }))

        Assert.assertEquals("/banners/amazon",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getNaturaBanners_shouldReturnRandomBanners() = runBlocking {
        val firstBanner = makeBanner()
        val secondBanner = makeBanner()
        val banners = listOf(firstBanner, secondBanner)

        val bannersJSON = makeBannerListJSON(banners)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(bannersJSON)
                .setResponseCode(200))

        val bannersResponse = apiService.getNaturaBanners()

        Assert.assertTrue(bannersResponse.containsAll(banners.map { it.asBannerNet() }))

        Assert.assertEquals("/banners/natura",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getAvonBanners_shouldReturnRandomBanners() = runBlocking {
        val firstBanner = makeBanner()
        val secondBanner = makeBanner()
        val banners = listOf(firstBanner, secondBanner)

        val bannersJSON = makeBannerListJSON(banners)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(bannersJSON)
                .setResponseCode(200))

        val bannersResponse = apiService.getAvonBanners()

        Assert.assertTrue(bannersResponse.containsAll(banners.map { it.asBannerNet() }))

        Assert.assertEquals("/banners/avon",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getContact_shouldReturnRandomContactInfo() = runBlocking {
        val contactInfo = makeContactInfo()

        val contactInfoJSON = makeContactInfoJSON(contactInfo)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(contactInfoJSON)
                .setResponseCode(200))

        val contactInfoResponse = apiService.getContact()

        Assert.assertEquals(contactInfoResponse.asContactInfo(), contactInfo)

        Assert.assertEquals("/contact",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getBlogPosts_shouldReturnRandomPosts() = runBlocking {
        val firstBlogPost = makeBlogPost()
        val secondBlogPost = makeBlogPost()
        val blogPosts = listOf(firstBlogPost, secondBlogPost)

        val blogPostsJSON = makeBlogPostListJSON(blogPosts)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(blogPostsJSON)
                .setResponseCode(200))

        val blogPostsResponse = apiService.getBlogPosts()

        Assert.assertTrue(blogPostsResponse.containsAll(blogPosts.map { it.asBlogPostNet() }))

        Assert.assertEquals("/posts",
            mockWebServer.takeRequest().path)
    }

    @Test
    fun getOfferNotification_shouldReturnRandomNotification() = runBlocking {
        val notification = makeNotification()

        val notificationJSON = makeNotificationJSON(notification)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(notificationJSON)
                .setResponseCode(200))

        val notificationResponse = apiService.getOfferNotification()

        Assert.assertEquals(notification.description, notificationResponse.description)

        Assert.assertEquals("/notification/offer",
            mockWebServer.takeRequest().path)
    }
}