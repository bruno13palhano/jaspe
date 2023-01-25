package com.example.network.service.contact

import com.bruno13palhano.model.ContactInfo
import com.example.network.model.asContactInfo
import com.example.network.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ContactInfoNetworkImp : ContactInfoNetwork {
    override suspend fun getContactInfo(): Flow<ContactInfo>  = flow {
        try {
            emit(ApiService.ProductApi.apiService.getContact().asContactInfo())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}