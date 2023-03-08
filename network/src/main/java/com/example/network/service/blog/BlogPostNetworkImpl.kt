package com.example.network.service.blog

import com.bruno13palhano.model.BlogPost
import com.example.network.model.asBlogPost
import com.example.network.service.ApiService
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScoped
internal class BlogPostNetworkImpl @Inject constructor(

) : BlogPostNetwork {
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