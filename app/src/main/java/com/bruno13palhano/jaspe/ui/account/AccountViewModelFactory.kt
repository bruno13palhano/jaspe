package com.bruno13palhano.jaspe.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.repository.external.UserRepository

class AccountViewModelFactory(
    private val authentication: UserAuthentication,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(authentication, userRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}