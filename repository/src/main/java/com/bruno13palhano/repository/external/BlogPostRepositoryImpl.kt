package com.bruno13palhano.repository.external

import com.bruno13palhano.model.BlogPost
import com.bruno13palhano.repository.dao.BlogPostDao
import com.bruno13palhano.repository.model.asBlogPost
import com.bruno13palhano.repository.model.asBlogPostRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BlogPostRepositoryImpl(
    private val dao: BlogPostDao
) : BlogPostRepository {
    override suspend fun insertAllBlogPosts(blogPostList: List<BlogPost>) {
        dao.insertAll(blogPostList.map {
            it.asBlogPostRep()
        })
    }

    override fun getAllBlogPosts(): Flow<List<BlogPost>> {
        return dao.getAllBlogPost().map {
            it.map { blogPostRep ->
                blogPostRep.asBlogPost()
            }
        }
    }
}