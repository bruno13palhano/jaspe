package com.bruno13palhano.jaspe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bruno13palhano.jaspe.work.ProductWork
import java.util.concurrent.TimeUnit

class MainViewModel(
    application: Application
) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)
    private val fetchData =
            PeriodicWorkRequestBuilder<ProductWork>(1, TimeUnit.HOURS)
                .build()

    fun fetchDataFromServe() {
        workManager.enqueue(fetchData)
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
