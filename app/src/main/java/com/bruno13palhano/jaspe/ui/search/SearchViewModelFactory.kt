package com.bruno13palhano.jaspe.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.ProductRepository

class SearchViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}