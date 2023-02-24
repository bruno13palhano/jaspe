package com.bruno13palhano.repository.blog

import com.bruno13palhano.model.BlogPost
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object BlogFactory {

    fun createBlogPost(): BlogPost {
        return BlogPost(
            postId = makeRandomLong(),
            postTitle = makeRandomString(),
            postDescription = makeRandomString(),
            postUrlLink = makeRandomString(),
            postUrlImage = makeRandomString()
        )
    }

    fun makeRandomString() = UUID.randomUUID().toString()

    fun makeRandomLong() = ThreadLocalRandom.current()
        .nextLong(0, 1000)
}