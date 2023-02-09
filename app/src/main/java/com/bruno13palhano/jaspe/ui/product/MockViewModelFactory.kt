package com.bruno13palhano.jaspe.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.FavoriteProductRepository
import com.bruno13palhano.repository.external.ProductRepository

class MockViewModelFactory(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val contactInfoRepository: ContactInfoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MockViewModel::class.java)) {
            return MockViewModel(
                productRepository = productRepository,
                favoriteProductRepository = favoriteProductRepository,
                contactInfoRepository = contactInfoRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}