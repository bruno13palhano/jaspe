package com.bruno13palhano.jaspe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bruno13palhano.jaspe.work.*
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.repository.di.DefaultContactInfoRepository
import com.bruno13palhano.repository.repository.contact.ContactInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,

    @DefaultContactInfoRepository
    private val contactInfoRepository: ContactInfoRepository
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
        initWorks()

        viewModelScope.launch {
            try {
                contactInfoRepository.getContactInfo(1L).collect {
                    _contactInfo.value = it
                }
            } catch (ignored: Exception) {}
        }
    }

    private fun initWorks() {
        setWork(WorkNames.PRODUCT, workManager, fetchProductData)
        setWork(WorkNames.BANNER, workManager, fetchBannerData)
        setWork(WorkNames.CONTACT, workManager, fetchContactData)
        setWork(WorkNames.NOTIFICATION, workManager, fetchNotificationData)
    }

    private fun setWork(
        workName: WorkNames,
        workManager: WorkManager,
        periodicWorkRequest: PeriodicWorkRequest
    ) {
        workManager.enqueueUniquePeriodicWork(
            workName.tag, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)
    }
}
