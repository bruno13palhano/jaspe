package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.ProductRepository

class HomeViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}