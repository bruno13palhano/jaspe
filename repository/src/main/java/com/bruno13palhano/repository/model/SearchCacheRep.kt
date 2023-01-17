package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bruno13palhano.model.SearchCache

@Entity(tableName = "search_cache_table")
internal data class SearchCacheRep(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "search_cache_id")
    val searchCacheId: Long,

    @ColumnInfo(name = "search_cache_name")
    val searchCacheName: String
)

internal fun SearchCacheRep.asSearchCache() = SearchCache(
    searchCacheId = searchCacheId,
    searchCacheName = searchCacheName
)

internal fun SearchCache.asSearchCacheRep() = SearchCacheRep(
    searchCacheId = searchCacheId,
    searchCacheName = searchCacheName
)