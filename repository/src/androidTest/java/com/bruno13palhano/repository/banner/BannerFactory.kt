package com.bruno13palhano.repository.banner

import com.bruno13palhano.model.Banner
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object BannerFactory {

    fun createBanner(): Banner {
        return Banner(
            bannerId = makeRandomLong(),
            bannerName = makeRandomString(),
            bannerUrlImage = makeRandomString(),
            bannerCompany = makeRandomString()
        )
    }

    fun makeRandomString() = UUID.randomUUID().toString()

    fun makeRandomLong() = ThreadLocalRandom.current()
        .nextLong(0, 1000)
}