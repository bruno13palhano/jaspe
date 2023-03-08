package com.bruno13palhano.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.bruno13palhano.repository.model.BannerRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BannerDao {

    @Insert
    suspend fun insert(banner: BannerRep)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(bannerList: List<BannerRep>)

    @Update
    suspend fun update(banner: BannerRep)

    @Delete
    suspend fun delete(banner: BannerRep)

    @Query("DELETE FROM banner_table WHERE banner_id = :bannerId")
    suspend fun deleteById(bannerId: Long)

    @Query("SELECT * FROM banner_table WHERE banner_id = :bannerId")
    fun getBannerById(bannerId: Long): Flow<BannerRep>

    @Query("SELECT * FROM banner_table")
    fun getAll(): Flow<List<BannerRep>>

    @Query("SELECT * FROM banner_table WHERE banner_company = :bannerCompany "+
        "ORDER BY banner_id DESC LIMIT :offset,:limit")
    fun getByCompany(
        bannerCompany: String,
        offset: Int,
        limit: Int
    ): Flow<List<BannerRep>>
}