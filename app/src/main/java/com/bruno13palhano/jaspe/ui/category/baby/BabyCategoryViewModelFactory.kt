package com.bruno13palhano.jaspe.ui.category.baby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.ProductRepository

class BabyCategoryViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BabyCategoryViewModel::class.java)) {
            return BabyCategoryViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}