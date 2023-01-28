package com.bruno13palhano.jaspe.ui.category.lastseen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.ProductRepository

class LastSeenCategoryViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LastSeenCategoryViewModel::class.java)) {
            return LastSeenCategoryViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}