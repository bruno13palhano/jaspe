package com.bruno13palhano.jaspe.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.SearchCache
import com.bruno13palhano.repository.di.DefaultSearchCacheRepository
import com.bruno13palhano.repository.repository.cache.SearchCacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchDialogViewModel @Inject constructor(
    @DefaultSearchCacheRepository
    private val searchCacheRepository: SearchCacheRepository
) : ViewModel() {

    val searchCache: StateFlow<List<SearchCache>> =
        searchCacheRepository.getAllSearchCache()
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

    fun insertSearchCache(searchCache: SearchCache) {
        viewModelScope.launch {
            searchCacheRepository.insertSearchCache(searchCache)
        }
    }

    fun deleteSearchCacheById(searchCacheId: Long) {
        viewModelScope.launch {
            searchCacheRepository.deleteSearchCacheById(searchCacheId)
        }
    }
}