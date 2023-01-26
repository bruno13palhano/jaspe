package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.BannerRepository
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.ProductRepository

class HomeViewModelFactory(
    private val productRepository: ProductRepository,
    private val bannerRepository: BannerRepository,
    private val contactInfoRepository: ContactInfoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(productRepository, bannerRepository, contactInfoRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}