package com.bruno13palhano.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bruno13palhano.repository.model.ProductRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductDao {

    @Insert
    suspend fun insert(product: ProductRep)

    @Insert
    suspend fun insertAll(productList: List<ProductRep>)

    @Update
    suspend fun update(product: ProductRep)

    @Delete
    suspend fun delete(product: ProductRep)

    @Query("DELETE FROM product_table WHERE product_id = :productId")
    suspend fun deleteById(productId: Long)

    @Query("SELECT * FROM product_table WHERE product_id = :productId")
    fun get(productId: Long): Flow<ProductRep>

    @Query("SELECT * FROM product_table")
    fun getAll(): Flow<List<ProductRep>>

    @Query("SELECT * FROM product_table WHERE product_favorite = 1")
    fun getAllFavorites(): Flow<List<ProductRep>>

    @Query("SELECT * FROM product_table WHERE product_company = :productCompany " +
            "ORDER BY product_id DESC LIMIT :offset,:limit")
    fun getByCompany(productCompany: String, offset: Int, limit: Int): Flow<List<ProductRep>>
}