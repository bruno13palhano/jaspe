package com.example.network.service.blog

import com.bruno13palhano.model.BlogPost
import com.example.network.model.asBlogPost
import com.example.network.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class BlogPostNetworkImpl : BlogPostNetwork {
    override suspend fun getBlogPosts(): Flow<List<BlogPost>> = flow {
        try {
            emit(ApiService.ProductApi.apiService.getBlogPosts().map {
                it.asBlogPost()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}