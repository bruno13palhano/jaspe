package com.bruno13palhano.jaspe.ui.category.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.ProductRepository

class CategoriesViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            return CategoriesViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}