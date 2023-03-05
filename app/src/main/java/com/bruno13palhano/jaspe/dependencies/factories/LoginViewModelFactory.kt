package com.bruno13palhano.jaspe.dependencies.factories

import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.ui.login.LoginViewModel
import com.bruno13palhano.repository.external.UserRepository

class LoginViewModelFactory(
    private val userRepository: UserRepository,
    private val authentication: UserAuthentication
) : Factory<LoginViewModel> {
    override fun create(): LoginViewModel {
        return LoginViewModel(userRepository, authentication)
    }
}