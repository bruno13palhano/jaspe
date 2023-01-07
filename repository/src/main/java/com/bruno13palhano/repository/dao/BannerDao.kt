package com.bruno13palhano.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bruno13palhano.repository.model.BannerRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BannerDao {

    @Insert
    suspend fun insert(banner: BannerRep)

    @Insert
    suspend fun insertAll(bannerList: List<BannerRep>)

    @Update fun update(banner: BannerRep)

    @Delete
    suspend fun delete(banner: BannerRep)

    @Query("DELETE FROM banner_table WHERE banner_id = :bannerId")
    suspend fun deleteById(bannerId: Long)

    @Query("SELECT * FROM banner_table WHERE banner_id = :bannerId")
    suspend fun getBannerById(bannerId: Long): Flow<ProductDao>

    @Query("SELECT * FROM banner_table")
    suspend fun getAll(): Flow<List<ProductDao>>

    @Query("SELECT * FROM banner_table WHERE banner_company = :bannerCompany "+
        "ORDER BY banner_id DESC LIMIT :offset,:limit")
    suspend fun getByCompany(
        bannerCompany: String,
        offset: Int,
        limit: Int
    ): Flow<List<BannerRep>>
}