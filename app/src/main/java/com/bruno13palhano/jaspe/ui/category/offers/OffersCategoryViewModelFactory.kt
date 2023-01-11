package com.bruno13palhano.jaspe.ui.category.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.ProductRepository

class OffersCategoryViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OffersCategoryViewModel::class.java)) {
            return OffersCategoryViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}