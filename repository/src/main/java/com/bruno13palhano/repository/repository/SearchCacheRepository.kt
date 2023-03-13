package com.bruno13palhano.repository.repository

import com.bruno13palhano.model.SearchCache
import kotlinx.coroutines.flow.Flow

interface SearchCacheRepository {
    suspend fun insertSearchCache(searchCache: SearchCache)
    suspend fun deleteSearchCacheById(searchCacheId: Long)
    fun getAllSearchCache(): Flow<List<SearchCache>>
}