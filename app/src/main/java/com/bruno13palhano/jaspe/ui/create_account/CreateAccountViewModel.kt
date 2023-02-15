package com.bruno13palhano.jaspe.ui.create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.model.User
import com.bruno13palhano.repository.external.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val userRepository: UserRepository,
    private val authentication: UserAuthentication
) : ViewModel() {
    private val _createAccountStatus =
        MutableStateFlow<CreateAccountStatus>(CreateAccountStatus.Default)
    val createAccountStatus = _createAccountStatus.asStateFlow()

    fun createAccount(
        username: String,
        email: String,
        password: String,
    ) {
        loading()
        val accountFirebase = AccountFirebase(authentication)
        accountFirebase.createUser(
            username = username,
            email = email,
            password = password,
            callback =  object : Account.CreateAccountCallback {
                override fun onSuccess(newUser: User) {
                    insertUser(newUser)
                    _createAccountStatus.value = CreateAccountStatus.Success
                }

                override fun onFail() {
                    _createAccountStatus.value = CreateAccountStatus.Error
                }
            }
        )
    }

    private fun loading() {
        _createAccountStatus.value = CreateAccountStatus.Loading
    }

    private fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }
}

sealed class CreateAccountStatus {
    object Loading: CreateAccountStatus()
    object Error: CreateAccountStatus()
    object Success: CreateAccountStatus()
    object Default: CreateAccountStatus()
}