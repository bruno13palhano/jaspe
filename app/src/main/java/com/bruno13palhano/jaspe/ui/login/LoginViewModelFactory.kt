package com.bruno13palhano.jaspe.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.repository.external.UserRepository

class LoginViewModelFactory(
    private val userRepository: UserRepository,
    private val authentication: UserAuthentication
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository, authentication) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}