package com.bruno13palhano.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.bruno13palhano.repository.model.ContactInfoRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ContactInfoDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(contactInfo: ContactInfoRep)

    @Query("SELECT * FROM contact_table WHERE contact_id = :contactInfoId")
    fun getContactInfo(contactInfoId: Long): Flow<ContactInfoRep>
}