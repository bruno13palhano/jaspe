package com.bruno13palhano.repository.repository.blog

import com.bruno13palhano.model.BlogPost
import kotlinx.coroutines.flow.Flow

interface BlogPostRepository {
    suspend fun insertAllBlogPosts(blogPostList: List<BlogPost>)
    fun getAllBlogPostsStream(): Flow<List<BlogPost>>
}