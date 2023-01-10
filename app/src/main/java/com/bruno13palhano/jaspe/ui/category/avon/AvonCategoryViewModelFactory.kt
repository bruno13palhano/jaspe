package com.bruno13palhano.jaspe.ui.category.avon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.ProductRepository

class AvonCategoryViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvonCategoryViewModel::class.java)) {
            return AvonCategoryViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}