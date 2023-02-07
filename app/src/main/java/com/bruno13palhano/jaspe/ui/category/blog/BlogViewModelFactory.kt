package com.bruno13palhano.jaspe.ui.category.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.BlogPostRepository

class BlogViewModelFactory(
    private val blogPostRepository: BlogPostRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlogViewModel::class.java)) {
            return BlogViewModel(blogPostRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}