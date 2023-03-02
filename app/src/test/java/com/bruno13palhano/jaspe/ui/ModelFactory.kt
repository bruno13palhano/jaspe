package com.bruno13palhano.jaspe.ui.home

import com.bruno13palhano.model.*
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object ModelFactory {

    fun makeProduct() = Product(
        productId = makeRandomLong(),
        productName = makeRandomString(),
        productUrlImage = makeRandomString(),
        productPrice = makeRandomFloat(),
        productDescription = makeRandomString(),
        productCompany = makeRandomString(),
        productType = makeRandomString(),
        productUrlLink = makeRandomString()
    )

    fun makeProduct(company: String) = Product(
        productId = makeRandomLong(),
        productName = makeRandomString(),
        productUrlImage = makeRandomString(),
        productPrice = makeRandomFloat(),
        productDescription = makeRandomString(),
        productCompany = company,
        productType = makeRandomString(),
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

    fun makeUser() = User(
        uid = makeRandomString(),
        username = makeRandomString(),
        email = makeRandomString(),
        password = "",
        urlPhoto = makeRandomString()
    )

    private fun makeRandomString() = UUID.randomUUID().toString()

    private fun makeRandomLong() = ThreadLocalRandom.current()
        .nextLong(0, 1000)

    private fun makeRandomFloat() = ThreadLocalRandom.current()
        .nextFloat()

    private fun makeRandomBoolean() = ThreadLocalRandom.current()
        .nextBoolean()
}