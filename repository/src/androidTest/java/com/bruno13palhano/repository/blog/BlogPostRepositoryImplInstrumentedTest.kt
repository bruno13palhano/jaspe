package com.bruno13palhano.repository.blog

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.bruno13palhano.repository.database.JaspeDatabase
import com.bruno13palhano.repository.blog.BlogFactory.createBlogPost
import com.bruno13palhano.repository.database.dao.BlogPostDao
import com.bruno13palhano.repository.repository.blog.BlogPostRepository
import com.bruno13palhano.repository.repository.blog.BlogPostRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class BlogPostRepositoryImplInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var jaspeDatabase: JaspeDatabase
    private lateinit var blogPostDao: BlogPostDao
    private lateinit var repository: BlogPostRepository

    @Before
    fun initDB() {
        jaspeDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            JaspeDatabase::class.java
        ).build()
        blogPostDao = jaspeDatabase.blogPostDao
        repository = BlogPostRepositoryImpl(blogPostDao)
    }

    @After
    fun closeDB() {
        jaspeDatabase.close()
    }

    @Test
    fun insertBlogPosts_shouldPersistBlogPostsInDB() = runBlocking {
        val blogPost = createBlogPost()

        repository.insertAllBlogPosts(listOf(blogPost))
        repository.getAllBlogPosts().test {
            val currentBlogPost = awaitItem()[0]

            Assert.assertEquals(currentBlogPost.postId, blogPost.postId)
        }
    }

    @Test
    fun getAllBlogPosts_shouldReturnAListFlow() = runBlocking {
        val firstBlogPost = createBlogPost()
        val secondBlogPost = createBlogPost()
        val blogPosts = listOf(firstBlogPost, secondBlogPost)

        repository.insertAllBlogPosts(blogPosts)
        repository.getAllBlogPosts().test {
            val currentBlogPosts = awaitItem()

            Assert.assertTrue(currentBlogPosts.containsAll(blogPosts))
            cancel()
        }
    }
}