package com.bruno13palhano.jaspe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.UserRepository

class MainViewModelFactory(
    private val application: Application,
    private val contactInfoRepository: ContactInfoRepository,
    private val userRepository: UserRepository,
    private val authentication: UserAuthentication
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                application = application,
                contactInfoRepository = contactInfoRepository,
                userRepository = userRepository,
                authentication = authentication
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}