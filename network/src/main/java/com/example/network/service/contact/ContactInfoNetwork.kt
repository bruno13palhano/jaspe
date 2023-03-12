package com.example.network.service.contact

import com.bruno13palhano.model.ContactInfo
import kotlinx.coroutines.flow.Flow

interface ContactInfoNetwork {
    fun getContactInfo(): Flow<ContactInfo>
}