package com.bruno13palhano.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.bruno13palhano.repository.model.LastSeenRep
import com.bruno13palhano.repository.model.ProductRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductDao {

    @Insert
    suspend fun insert(product: ProductRep)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(productList: List<ProductRep>)

    @Update
    suspend fun update(product: ProductRep)

    @Delete
    suspend fun delete(product: ProductRep)

    @Query("DELETE FROM product_table WHERE product_id = :productId")
    suspend fun deleteById(productId: Long)

    @Query("SELECT * FROM product_table WHERE product_id = :productId")
    fun get(productId: Long): Flow<ProductRep>

    @Query("SELECT * FROM product_table WHERE product_url_link = :productLink")
    fun getByLink(productLink: String): Flow<ProductRep>

    @Query("SELECT * FROM product_table")
    fun getAll(): Flow<List<ProductRep>>

    @Query("SELECT * FROM product_table WHERE product_company = :productCompany " +
            "ORDER BY product_id DESC LIMIT :offset,:limit")
    fun getByCompany(productCompany: String, offset: Int, limit: Int): Flow<List<ProductRep>>

    @Query("SELECT * FROM product_table WHERE product_type = :productType")
    fun getByType(productType: String): Flow<List<ProductRep>>

    @Query(
        "SELECT * FROM product_table WHERE product_name LIKE '%'||:searchValue||'%' " +
                "OR product_type LIKE '%'||:searchValue||'%' " +
                "OR product_description LIKE '%'||:searchValue||'%'"
    )
    fun searchProduct(searchValue: String): Flow<List<ProductRep>>

    @Query("SELECT * FROM product_table WHERE " +
            "product_url_link = :productUrLink")
    fun getProductByLink(productUrLink: String): Flow<ProductRep>

    @Insert(onConflict = REPLACE)
    suspend fun insertLastSeen(product: LastSeenRep)

    @Query("DELETE FROM last_seen_product_table WHERE last_seen_product_url_link = :productUrlLink")
    suspend fun deleteLastSeenByUrlLink(productUrlLink: String)

    @Query("SELECT * FROM last_seen_product_table")
    fun getAllLastSeen(): Flow<List<LastSeenRep>>

    @Query("SELECT * FROM last_seen_product_table WHERE last_seen_product_url_link = :productUrlLInk")
    fun getLastSeenProduct(productUrlLInk: String): Flow<LastSeenRep>

    @Query("SELECT * FROM last_seen_product_table "+
            "ORDER BY last_seen_product_id DESC LIMIT " +
            ":offset, :limit")
    fun getLastSeenProducts(offset: Int, limit: Int): Flow<List<LastSeenRep>>
}