package com.bruno13palhano.jaspe.ui.category.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.BlogPost
import com.bruno13palhano.repository.di.DefaultBannerRepository
import com.bruno13palhano.repository.di.DefaultBlogPostRepository
import com.bruno13palhano.repository.repository.BlogPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    @DefaultBlogPostRepository
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