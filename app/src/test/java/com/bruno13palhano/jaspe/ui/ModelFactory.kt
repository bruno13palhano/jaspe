package com.bruno13palhano.jaspe.ui

import com.bruno13palhano.model.*
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object ModelFactory {

    fun makeProduct(
        company: String = makeRandomString(),
        type: String = makeRandomString()
    ) = Product(
        productId = makeRandomLong(),
        productName = makeRandomString(),
        productUrlImage = makeRandomString(),
        productPrice = makeRandomFloat(),
        productDescription = makeRandomString(),
        productCompany = company,
        productType = type,
        productUrlLink = makeRandomString()
    )

    fun makeFavoriteProduct(
        isVisible: Boolean = makeRandomBoolean()
    ) = FavoriteProduct(
        favoriteProductId = makeRandomLong(),
        favoriteProductName = makeRandomString(),
        favoriteProductUrlImage = makeRandomString(),
        favoriteProductPrice = makeRandomFloat(),
        favoriteProductType = makeRandomString(),
        favoriteProductDescription = makeRandomString(),
        favoriteProductCompany = makeRandomString(),
        favoriteProductUrlLink = makeRandomString(),
        favoriteProductIsVisible = isVisible
    )

    fun makeBanner(company: String) = Banner(
        bannerId = makeRandomLong(),
        bannerName = makeRandomString(),
        bannerUrlImage = makeRandomString(),
        bannerCompany = company
    )

    fun makeNotification(isVisualized: Boolean) = Notification(
        id = makeRandomLong(),
        title = makeRandomString(),
        description = makeRandomString(),
        isVisualized = isVisualized,
        type = makeRandomString()
    )

    fun makeContactInfo() = ContactInfo(
        contactWhatsApp = makeRandomString(),
        contactInstagram = makeRandomString(),
        contactEmail = makeRandomString()
    )

    fun makeUser(uid: String = makeRandomString()) = User(
        uid = uid,
        username = makeRandomString(),
        email = makeRandomString(),
        password = "",
        urlPhoto = makeRandomString()
    )

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
        .nextFloat()

    private fun makeRandomBoolean() = ThreadLocalRandom.current()
        .nextBoolean()
}