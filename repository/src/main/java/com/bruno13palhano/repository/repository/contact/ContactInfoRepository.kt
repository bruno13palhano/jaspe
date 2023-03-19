package com.bruno13palhano.repository.repository.contact

import com.bruno13palhano.model.ContactInfo
import kotlinx.coroutines.flow.Flow

interface ContactInfoRepository {
    suspend fun insertContactInfo(contactInfo: ContactInfo)
    fun getContactInfo(contactInfoId: Long): Flow<ContactInfo>
}