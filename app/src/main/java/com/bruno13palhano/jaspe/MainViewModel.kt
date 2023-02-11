package com.bruno13palhano.jaspe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bruno13palhano.jaspe.work.BannerWork
import com.bruno13palhano.jaspe.work.ContactWork
import com.bruno13palhano.jaspe.work.NotificationWork
import com.bruno13palhano.jaspe.work.ProductWork
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.repository.external.ContactInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModel(
    application: Application,
    contactInfoRepository: ContactInfoRepository
) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)
    private val fetchProductData =
            PeriodicWorkRequestBuilder<ProductWork>(1, TimeUnit.HOURS)
                .build()
    private val fetchBannerData =
        PeriodicWorkRequestBuilder<BannerWork>(1, TimeUnit.HOURS)
            .build()

    private val fetchContactData =
        PeriodicWorkRequestBuilder<ContactWork>(1, TimeUnit.HOURS)
            .build()

    private val fetchNotificationData =
        PeriodicWorkRequestBuilder<NotificationWork>(15, TimeUnit.MINUTES)
            .build()

    private val _contactInfo = MutableStateFlow(ContactInfo())
    val contactInfo = _contactInfo.asStateFlow()

    init {
        workManager.enqueueUniquePeriodicWork(
            "PRODUCT_WORK", ExistingPeriodicWorkPolicy.KEEP, fetchProductData)
        workManager.enqueueUniquePeriodicWork(
            "BANNER_WORK", ExistingPeriodicWorkPolicy.KEEP, fetchBannerData)
        workManager.enqueueUniquePeriodicWork(
            "CONTACT_WORK", ExistingPeriodicWorkPolicy.KEEP, fetchContactData)
        workManager.enqueueUniquePeriodicWork(
            "NOTIFICATION_WORK", ExistingPeriodicWorkPolicy.KEEP, fetchNotificationData)

        viewModelScope.launch {
            try {
                contactInfoRepository.getContactInfo(1L).collect {
                    _contactInfo.value = it
                }
            } catch (ignored: Exception) {}
        }
    }
}
