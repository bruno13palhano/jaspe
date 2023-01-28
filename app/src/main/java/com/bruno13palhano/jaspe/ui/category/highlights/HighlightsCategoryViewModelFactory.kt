package com.bruno13palhano.jaspe.ui.category.highlights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.ProductRepository

class HighlightsCategoryViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighlightsCategoryViewModel::class.java)) {
            return HighlightsCategoryViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}