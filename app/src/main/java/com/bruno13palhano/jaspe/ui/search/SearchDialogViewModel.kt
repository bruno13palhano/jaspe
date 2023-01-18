package com.bruno13palhano.jaspe.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.SearchCache
import com.bruno13palhano.repository.SearchCacheRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchDialogViewModel(
    private val searchCacheRepository: SearchCacheRepository
) : ViewModel() {

    private val _searchCache = MutableStateFlow<List<SearchCache>>(emptyList())
    val searchCache: StateFlow<List<SearchCache>> = _searchCache

    init {
        viewModelScope.launch {
            searchCacheRepository.getAllSearchCache().collect {
                _searchCache.value = it
            }
        }
    }

    suspend fun insertSearchCache(searchCache: SearchCache) {
        viewModelScope.launch {
            searchCacheRepository.insertSearchCache(searchCache)
        }
    }

    suspend fun deleteSearchCacheById(searchCacheId: Long) {
        viewModelScope.launch {
            searchCacheRepository.deleteSearchCacheById(searchCacheId)
        }
    }
}