package com.bruno13palhano.jaspe.ui.category.blog

import androidx.lifecycle.ViewModel
import com.bruno13palhano.repository.external.BlogPostRepository

class BlogPostViewModel(
    private val blogPostRepository: BlogPostRepository
) : ViewModel() {

}