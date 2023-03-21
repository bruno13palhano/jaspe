package com.example.network.service.blog

import com.bruno13palhano.model.BlogPost
import kotlinx.coroutines.flow.Flow

interface BlogPostNetwork {
    fun getBlogPostsStream(): Flow<List<BlogPost>>
}