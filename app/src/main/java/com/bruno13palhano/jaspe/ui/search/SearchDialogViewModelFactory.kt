package com.bruno13palhano.jaspe.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.SearchCacheRepository

class SearchDialogViewModelFactory(
    private val searchCacheRepository: SearchCacheRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchDialogViewModel::class.java)) {
            return SearchDialogViewModel(searchCacheRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}