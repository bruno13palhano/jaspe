package com.bruno13palhano.jaspe.dependencies.factories

import android.app.Application
import com.bruno13palhano.jaspe.MainViewModel
import com.bruno13palhano.repository.external.ContactInfoRepository

class MainViewModelFactory(
    private val application: Application,
    private val contactInfoRepository: ContactInfoRepository
) : Factory<MainViewModel> {
    override fun create(): MainViewModel {
        return MainViewModel(application, contactInfoRepository)
    }
}