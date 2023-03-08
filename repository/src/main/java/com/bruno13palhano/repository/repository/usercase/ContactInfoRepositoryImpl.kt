package com.bruno13palhano.repository.repository.usercase

import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.repository.database.dao.ContactInfoDao
import com.bruno13palhano.repository.model.asContactInfo
import com.bruno13palhano.repository.model.asContactInfoRep
import com.bruno13palhano.repository.repository.ContactInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ContactInfoRepositoryImpl(
    private val dao: ContactInfoDao
) : ContactInfoRepository {
    override suspend fun insertContactInfo(contactInfo: ContactInfo) {
        dao.insert(contactInfo.asContactInfoRep())
    }

    override fun getContactInfo(contactInfoId: Long): Flow<ContactInfo> {
        return dao.getContactInfo(contactInfoId).map {
            it.asContactInfo()
        }
    }
}