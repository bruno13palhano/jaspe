package com.bruno13palhano.repository.external

import com.bruno13palhano.model.SearchCache
import kotlinx.coroutines.flow.Flow

interface SearchCacheRepository {
    suspend fun insertSearchCache(searchCache: SearchCache)
    suspend fun deleteSearchCacheById(searchCacheId: Long)
    suspend fun getAllSearchCache(): Flow<List<SearchCache>>
}