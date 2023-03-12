package com.example.network.service.contact

import com.bruno13palhano.model.ContactInfo
import com.example.network.model.asContactInfo
import com.example.network.service.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ContactInfoNetworkImp @Inject constructor(
    private val apiService: Service
) : ContactInfoNetwork {
    override fun getContactInfo(): Flow<ContactInfo>  = flow {
        try {
            emit(apiService.getContact().asContactInfo())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}