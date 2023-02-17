package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.repository.external.BannerRepository
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.ProductRepository
import com.bruno13palhano.repository.external.UserRepository

class HomeViewModelFactory(
    private val productRepository: ProductRepository,
    private val bannerRepository: BannerRepository,
    private val contactInfoRepository: ContactInfoRepository,
    private val userRepository: UserRepository,
    private val authentication: UserAuthentication
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                productRepository = productRepository,
                bannerRepository = bannerRepository,
                contactInfoRepository = contactInfoRepository,
                userRepository = userRepository,
                authentication = authentication
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}