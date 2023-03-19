package com.bruno13palhano.jaspe.ui.category.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.repository.di.DefaultBlogPostRepository
import com.bruno13palhano.repository.repository.BlogPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    @DefaultBlogPostRepository
    private val blogPostRepository: BlogPostRepository
) : ViewModel() {

    val allBlogPosts = blogPostRepository.getAllBlogPosts()
        .stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = WhileSubscribed(5_000)
        )
}