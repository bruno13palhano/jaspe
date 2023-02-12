package com.bruno13palhano.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.bruno13palhano.repository.model.UserRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface UserDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(user: UserRep)

    @Query("SELECT * FROM user_table WHERE user_id = :userUid")
    fun getUserByUid(userUid: String): Flow<UserRep>
}