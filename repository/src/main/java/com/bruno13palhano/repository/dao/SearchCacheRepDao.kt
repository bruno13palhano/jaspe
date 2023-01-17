package com.bruno13palhano.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bruno13palhano.repository.model.SearchCacheRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface SearchCacheRepDao {

    @Insert
    suspend fun insert(searchCache: SearchCacheRep)

    @Query("DELETE FROM search_cache_table WHERE search_cache_id = :searchCacheId")
    suspend fun deleteById(searchCacheId: Long)

    @Query("SELECT * FROM search_cache_table")
    fun getAll(): Flow<List<SearchCacheRep>>
}