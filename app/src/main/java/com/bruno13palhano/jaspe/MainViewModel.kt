package com.bruno13palhano.jaspe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bruno13palhano.jaspe.work.BannerWork
import com.bruno13palhano.jaspe.work.ContactWork
import com.bruno13palhano.jaspe.work.ProductWork
import java.util.concurrent.TimeUnit

class MainViewModel(
    application: Application
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

    fun fetchDataFromServe() {
        workManager.enqueueUniquePeriodicWork(
            "PRODUCT_WORK", ExistingPeriodicWorkPolicy.KEEP, fetchProductData)
        workManager.enqueueUniquePeriodicWork(
            "BANNER_WORK", ExistingPeriodicWorkPolicy.KEEP, fetchBannerData)
        workManager.enqueueUniquePeriodicWork(
            "CONTACT_WORK", ExistingPeriodicWorkPolicy.KEEP, fetchContactData)
    }
}
