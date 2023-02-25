package com.bruno13palhano.repository.favorite

import com.bruno13palhano.model.FavoriteProduct
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object FavoriteFactory {

    fun createFavorite(): FavoriteProduct = FavoriteProduct(
        favoriteProductId = makeRandomLong(),
        favoriteProductName = makeRandomString(),
        favoriteProductUrlImage = makeRandomString(),
        favoriteProductPrice = makeRandomFloat(),
        favoriteProductType = makeRandomString(),
        favoriteProductDescription = makeRandomString(),
        favoriteProductCompany = makeRandomString(),
        favoriteProductUrlLink = makeRandomString(),
        favoriteProductIsVisible = makeRandomBoolean()
    )

    fun createFavorite(isVisible: Boolean): FavoriteProduct = FavoriteProduct(
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

    fun makeRandomString() = UUID.randomUUID().toString()

    private fun makeRandomLong() = ThreadLocalRandom.current()
        .nextLong(1, 1000)

    private fun makeRandomFloat() = ThreadLocalRandom.current()
        .nextFloat() * 1000

    private fun makeRandomBoolean() = ThreadLocalRandom.current()
        .nextBoolean()
}