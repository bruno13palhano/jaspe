package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.BannerRepository
import com.bruno13palhano.repository.ContactInfoRepository
import com.bruno13palhano.repository.ProductRepository

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