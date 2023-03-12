package com.example.network.service.blog

import com.bruno13palhano.common.di.Dispatcher
import com.bruno13palhano.common.di.ShopDaniDispatchers.IO
import com.bruno13palhano.model.BlogPost
import com.example.network.model.asBlogPost
import com.example.network.service.Service
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BlogPostNetworkImpl @Inject constructor(
    private val apiService: Service,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher
) : BlogPostNetwork {
    override fun getBlogPosts(): Flow<List<BlogPost>> = flow {
        try {
            emit(apiService.getBlogPosts().map {
                it.asBlogPost()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(dispatcher)
}