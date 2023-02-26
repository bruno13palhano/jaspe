package com.bruno13palhano.repository.search

import com.bruno13palhano.model.SearchCache
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object SearchCacheFactory {

    fun makeSearchCache() = SearchCache(
        searchCacheId = makeRandomLong(),
        searchCacheName = makeRandomString()
    )

    fun makeRandomString() = UUID.randomUUID().toString()

    fun makeRandomLong() = ThreadLocalRandom.current()
        .nextLong(1, 1000)
}