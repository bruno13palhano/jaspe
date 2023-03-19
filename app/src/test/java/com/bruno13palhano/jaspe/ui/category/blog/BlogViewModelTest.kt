package com.bruno13palhano.jaspe.ui.category.blog

import app.cash.turbine.test
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.jaspe.ui.ModelFactory.makeBlogPost
import com.bruno13palhano.model.BlogPost
import com.bruno13palhano.repository.repository.blog.BlogPostRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class BlogViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var blogPostRepository: BlogPostRepository

    private lateinit var viewModel: BlogViewModel

    private lateinit var blogPosts: List<BlogPost>

    @Before
    fun setUp() {
        blogPosts = listOf(makeBlogPost(), makeBlogPost(), makeBlogPost())

        doReturn(flow { emit(blogPosts) }).`when`(blogPostRepository)
            .getAllBlogPosts()

        viewModel = BlogViewModel(blogPostRepository)
    }

    @Test
    fun allBlogPosts_shouldReturnBlogPostsInFlow() = runTest {
        viewModel.allBlogPosts.test {
            val currentBlogPosts = awaitItem()

            Assert.assertTrue(currentBlogPosts.containsAll(blogPosts))
            cancel()
        }
    }
}