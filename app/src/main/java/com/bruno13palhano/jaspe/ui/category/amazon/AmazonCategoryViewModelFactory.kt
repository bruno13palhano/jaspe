package com.bruno13palhano.jaspe.ui.category.amazon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.ProductRepository

class AmazonCategoryViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AmazonCategoryViewModel::class.java)) {
            return AmazonCategoryViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}