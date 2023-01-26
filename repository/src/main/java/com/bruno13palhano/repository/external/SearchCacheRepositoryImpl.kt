package com.bruno13palhano.repository.external

import com.bruno13palhano.model.SearchCache
import com.bruno13palhano.repository.dao.SearchCacheRepDao
import com.bruno13palhano.repository.model.asSearchCache
import com.bruno13palhano.repository.model.asSearchCacheRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SearchCacheRepositoryImpl(
    private val daoCache: SearchCacheRepDao
) : SearchCacheRepository {
    override suspend fun insertSearchCache(searchCache: SearchCache) {
        daoCache.insert(searchCache.asSearchCacheRep())
    }

    override suspend fun deleteSearchCacheById(searchCacheId: Long) {
        daoCache.deleteById(searchCacheId)
    }

    override suspend fun getAllSearchCache(): Flow<List<SearchCache>> {
        return daoCache.getAll().map {
            it.map { searchCacheRep ->
                searchCacheRep.asSearchCache()
            }
        }
    }

}