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
) : ViewModel() {
    private val _loginStatus = MutableStateFlow<LoginStatus>(LoginStatus.Default)
    val loginStatus = _loginStatus.asStateFlow()

    fun login(email: String, password: String) {
        loading()
        val loginFirebase = LoginFirebase(authentication)
        loginFirebase.login(
            email = email,
            password = password,
            callback = object : Login.LoginCallback {
                override fun onSuccess(user: User) {
                    _loginStatus.value = LoginStatus.Success
                    insertUser(user)
                }

                override fun onFail() {
                    _loginStatus.value = LoginStatus.Error
                }
            }
        )
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

    private fun loading() {
        _loginStatus.value = LoginStatus.Loading
    }

    private fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }
}

sealed class LoginStatus {
    object Loading: LoginStatus()
    object Error : LoginStatus()
    object Success : LoginStatus()
    object Default : LoginStatus()
}