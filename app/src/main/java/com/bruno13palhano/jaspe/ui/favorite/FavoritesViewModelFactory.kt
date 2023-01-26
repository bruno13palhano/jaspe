package com.bruno13palhano.jaspe.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.FavoriteProductRepository

class FavoritesViewModelFactory(
    private val repository: FavoriteProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}