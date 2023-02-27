package com.example.network.model

import com.bruno13palhano.model.BlogPost
import com.squareup.moshi.Json

internal data class BlogPostNet(

    @Json(name = "postId")
    val postId: Long,

    @Json(name = "postTitle")
    val postTitle: String,

    @Json(name = "postDescription")
    val postDescription: String,

    @Json(name = "postUrlImage")
    val postUrlImage: String,

    @Json(name = "postUrlLink")
    val postUrlLink: String
)

internal fun BlogPostNet.asBlogPost() = BlogPost(
    postId = postId,
    postTitle = postTitle,
    postDescription = postDescription,
    postUrlImage = postUrlImage,
    postUrlLink = postUrlLink
)

internal fun BlogPost.asBlogPostNet() = BlogPostNet (
    postId = postId,
    postTitle = postTitle,
    postDescription = postDescription,
    postUrlImage = postUrlImage,
    postUrlLink = postUrlLink
)