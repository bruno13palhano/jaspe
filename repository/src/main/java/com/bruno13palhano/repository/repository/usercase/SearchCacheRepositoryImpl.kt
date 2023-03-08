package com.bruno13palhano.repository.repository.usercase

import com.bruno13palhano.model.SearchCache
import com.bruno13palhano.repository.database.dao.SearchCacheDao
import com.bruno13palhano.repository.model.asSearchCache
import com.bruno13palhano.repository.model.asSearchCacheRep
import com.bruno13palhano.repository.repository.SearchCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SearchCacheRepositoryImpl @Inject constructor(
    private val daoCache: SearchCacheDao
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