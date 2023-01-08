package com.bruno13palhano.jaspe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bruno13palhano.jaspe.work.BannerWork
import com.bruno13palhano.jaspe.work.ProductWork
import java.util.concurrent.TimeUnit

class MainViewModel(
    application: Application
) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)
    private val fetchProductData =
            PeriodicWorkRequestBuilder<ProductWork>(15, TimeUnit.MINUTES)
                .build()
    private val fetchBannerData =
        PeriodicWorkRequestBuilder<BannerWork>(15, TimeUnit.MINUTES)
            .build()

    fun fetchDataFromServe() {
        workManager.enqueue(fetchProductData)
        workManager.enqueue(fetchBannerData)
    }
}

class MainViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}
