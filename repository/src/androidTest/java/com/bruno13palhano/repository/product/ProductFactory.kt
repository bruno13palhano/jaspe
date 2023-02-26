package com.bruno13palhano.repository.product

import com.bruno13palhano.model.Product
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object ProductFactory {

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

    fun makeRandomString() = UUID.randomUUID().toString()

    private fun makeRandomLong() = ThreadLocalRandom.current()
        .nextLong(1, 1000)

    private fun makeRandomFloat() = ThreadLocalRandom.current()
        .nextFloat() * 1000
}