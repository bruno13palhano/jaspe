package com.example.network.service.notification

import com.bruno13palhano.common.di.Dispatcher
import com.bruno13palhano.common.di.ShopDaniDispatchers.IO
import com.bruno13palhano.model.Notification
import com.example.network.model.asOfferNotification
import com.example.network.service.Service
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NotificationNetworkImpl @Inject constructor(
    private val apiService: Service,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher
) : NotificationNetwork{
    override fun getOfferNotificationStream(): Flow<Notification> = flow {
        try {
            emit(apiService.getOfferNotification().asOfferNotification())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(dispatcher)
}