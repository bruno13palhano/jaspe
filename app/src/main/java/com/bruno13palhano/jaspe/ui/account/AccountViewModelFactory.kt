package com.bruno13palhano.jaspe.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.authentication.core.UserAuthentication

class AccountViewModelFactory(
    private val authentication: UserAuthentication
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(authentication) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}