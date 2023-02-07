package com.bruno13palhano.jaspe.ui.category.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.BlogPost
import com.bruno13palhano.repository.external.BlogPostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BlogViewModel(
    private val blogPostRepository: BlogPostRepository
) : ViewModel() {
    private val _allBlogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val allBlogPosts = _allBlogPosts.asStateFlow()

    init {
        viewModelScope.launch {
            blogPostRepository.getAllBlogPosts().collect {
                _allBlogPosts.value = it
            }
        }
    }
}