package com.example.network.service.offers

import com.bruno13palhano.model.OfferNotification
import kotlinx.coroutines.flow.Flow

interface OfferNotificationNetwork {
    suspend fun getOfferNotification(): Flow<OfferNotification>
}