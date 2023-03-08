package com.bruno13palhano.repository.repository.usercase

import com.bruno13palhano.model.BlogPost
import com.bruno13palhano.repository.database.dao.BlogPostDao
import com.bruno13palhano.repository.model.asBlogPost
import com.bruno13palhano.repository.model.asBlogPostRep
import com.bruno13palhano.repository.repository.BlogPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BlogPostRepositoryImpl @Inject constructor(
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