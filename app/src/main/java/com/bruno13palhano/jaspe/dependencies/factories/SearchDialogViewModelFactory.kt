package com.bruno13palhano.jaspe.dependencies.factories

import com.bruno13palhano.jaspe.ui.search.SearchDialogViewModel
import com.bruno13palhano.repository.external.SearchCacheRepository

class SearchDialogViewModelFactory(
    private val searchCacheRepository: SearchCacheRepository
) : Factory<SearchDialogViewModel>{
    override fun create(): SearchDialogViewModel {
        return SearchDialogViewModel(searchCacheRepository)
    }
}