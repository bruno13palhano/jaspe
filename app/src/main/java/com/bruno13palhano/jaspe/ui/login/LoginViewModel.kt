package com.bruno13palhano.jaspe.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.model.User
import com.bruno13palhano.repository.external.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val authentication: UserAuthentication
) : ViewModel(), Login {
    private val _loginStatus = MutableStateFlow<LoginStatus>(LoginStatus.Loading)
    val loginStatus = _loginStatus.asStateFlow()

    override fun login(email: String, password: String) {
        if (isParamsValid(email, password)) {
            authentication.login(
                email = email,
                password = password,
                successfulCallback = {
                    _loginStatus.value = LoginStatus.Success
                    insertUser(authentication.getCurrentUser())
                },
                failedCallback = {
                    _loginStatus.value = LoginStatus.Error
                }
            )
        } else {
            _loginStatus.value = LoginStatus.Error
        }
    }

    fun getUserByUid(userUid: String): Flow<User> {
        return userRepository.getUserByUid(userUid)
    }

    fun isUserAuthenticated(): Boolean {
        return if(authentication.isUserAuthenticated()) {
            insertUser(authentication.getCurrentUser())
            true
        } else {
            false
        }
    }

    private fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    private fun isParamsValid(email: String, password: String): Boolean =
        email != "" && password != ""
}

sealed class LoginStatus {
    object Loading: LoginStatus()
    object Error : LoginStatus()
    object Success : LoginStatus()
}