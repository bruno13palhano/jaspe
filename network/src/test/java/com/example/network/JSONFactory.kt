package com.example.network

import com.bruno13palhano.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object JSONFactory {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val productType = Types.newParameterizedType(List::class.java, Product::class.java)
    private val bannerType = Types.newParameterizedType(List::class.java, Banner::class.java)
    private val blogPostType = Types.newParameterizedType(List::class.java, BlogPost::class.java)

    private val productJSONAdapter = moshi.adapter<List<Product>>(productType)
    private val bannerJSONAdapter = moshi.adapter<List<Banner>>(bannerType)
    private val blogPostJSONAdapter = moshi.adapter<List<BlogPost>>(blogPostType)

    fun makeProductListJSON(products: List<Product>): String =
        productJSONAdapter.toJson(products)

    fun makeBannerListJSON(banners: List<Banner>): String =
        bannerJSONAdapter.toJson(banners)

    fun makeBlogPostListJSON(blogPosts: List<BlogPost>): String =
        blogPostJSONAdapter.toJson(blogPosts)

    fun makeProduct() = Product(
        productId = makeRandomLong(),
        productName = makeRandomString(),
        productUrlImage = makeRandomString(),
        productPrice = makeRandomFloat(),
        productType = makeRandomString(),
        productDescription = makeRandomString(),
        productCompany = makeRandomString(),
        productUrlLink = makeRandomString()
    )

    fun makeProductJSON(product: Product) = """
        { "productId": ${product.productId},
          "productName": "${product.productName}",
          "productUrlImage": "${product.productUrlImage}",
          "productPrice": "${product.productPrice}",
          "productType": "${product.productType}",
          "productDescription": "${product.productDescription}",
          "productCompany": "${product.productCompany}",
          "productUrlLink": "${product.productUrlLink}"}
    """.trimIndent()

    fun makeBanner() = Banner(
        bannerId = makeRandomLong(),
        bannerName = makeRandomString(),
        bannerUrlImage = makeRandomString(),
        bannerCompany = makeRandomString()
    )

    fun makeBannerJSON(banner: Banner) = """
        { "bannerId": ${banner.bannerId},
          "bannerName": "${banner.bannerName}",
          "bannerUrlImage": "${banner.bannerUrlImage}",
          "bannerCompany": "${banner.bannerCompany}"}
    """.trimIndent()

    fun makeContactInfo() = ContactInfo(
        contactWhatsApp = makeRandomString(),
        contactInstagram = makeRandomString(),
        contactEmail = makeRandomString()
    )

    fun makeContactInfoJSON(contactInfo: ContactInfo) = """
        { "contactWhatsApp": "${contactInfo.contactWhatsApp}",
          "contactInstagram": "${contactInfo.contactInstagram}",
          "contactEmail": "${contactInfo.contactEmail}"}
    """.trimIndent()

    fun makeNotification() = Notification(
        id = makeRandomLong(),
        title = makeRandomString(),
        description = makeRandomString(),
        isVisualized = makeRandomBoolean(),
        type = makeRandomString()
    )

    fun makeNotificationJSON(notification: Notification) = """
        { "title": "${notification.title}",
          "description": "${notification.description}",
          "type": "${notification.type}"}
    """.trimIndent()

    fun makeBlogPost() = BlogPost(
        postId = makeRandomLong(),
        postTitle = makeRandomString(),
        postDescription = makeRandomString(),
        postUrlImage = makeRandomString(),
        postUrlLink = makeRandomString()
    )

    private fun makeRandomString() = UUID.randomUUID().toString()

    private fun makeRandomLong() = ThreadLocalRandom.current()
        .nextLong(0, 1000)

    private fun makeRandomFloat() = ThreadLocalRandom.current()
        .nextFloat() * 1000

    private fun makeRandomBoolean() = ThreadLocalRandom.current()
        .nextBoolean()
}