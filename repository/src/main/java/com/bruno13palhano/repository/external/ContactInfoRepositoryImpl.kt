package com.bruno13palhano.repository.external

import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.repository.dao.ContactInfoDao
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.model.asContactInfo
import com.bruno13palhano.repository.model.asContactInfoRep
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