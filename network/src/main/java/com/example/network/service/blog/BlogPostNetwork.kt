package com.example.network.service.blog

import com.bruno13palhano.model.BlogPost
import kotlinx.coroutines.flow.Flow

interface BlogPostNetwork {
    suspend fun getBlogPosts(): Flow<List<BlogPost>>
}