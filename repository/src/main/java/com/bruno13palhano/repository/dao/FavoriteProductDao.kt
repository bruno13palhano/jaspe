package com.bruno13palhano.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.bruno13palhano.repository.model.FavoriteProductRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FavoriteProductDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(favoriteProduct: FavoriteProductRep)

    @Update
    suspend fun update(favoriteProduct: FavoriteProductRep)

    @Delete
    suspend fun delete(favoriteProduct: FavoriteProductRep)

    @Delete
    suspend fun deleteAll(favoriteProductList: List<FavoriteProductRep>)

    @Query("DELETE FROM favorite_product_table WHERE favorite_product_id = :favoriteProductId")
    suspend fun deleteFavoriteProductById(favoriteProductId: Long)

    @Query("SELECT * FROM favorite_product_table WHERE " +
            "favorite_product_id = :favoriteProductId")
    fun getFavorite(favoriteProductId: Long): Flow<FavoriteProductRep>

    @Query("SELECT * FROM favorite_product_table WHERE " +
            "favorite_product_url_link = :favoriteLink")
    fun getFavoriteByLink(favoriteLink: String): Flow<FavoriteProductRep>

    @Query("SELECT * FROM favorite_product_table")
    fun getAllFavorites(): Flow<List<FavoriteProductRep>>

    @Query("SELECT * FROM favorite_product_table WHERE favorite_product_is_visible = 1")
    fun getAllFavoritesVisible(): Flow<List<FavoriteProductRep>>

    @Query("UPDATE favorite_product_table SET favorite_product_is_visible = :favoriteProductIsFavorite " +
            "WHERE favorite_product_id = :favoriteProductId")
    suspend fun setFavoriteProductVisibility(favoriteProductId: Long, favoriteProductIsFavorite: Boolean)
}