package com.bruno13palhano.repository.repository

import com.bruno13palhano.model.BlogPost
import kotlinx.coroutines.flow.Flow

interface BlogPostRepository {
    suspend fun insertAllBlogPosts(blogPostList: List<BlogPost>)
    fun getAllBlogPosts(): Flow<List<BlogPost>>
}