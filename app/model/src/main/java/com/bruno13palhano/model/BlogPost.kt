package com.bruno13palhano.model

data class BlogPost(
    val postId: Long,
    val postTitle: String,
    val postDescription: String,
    val postUrlImage: String,
    val postUrlLink: String
)
