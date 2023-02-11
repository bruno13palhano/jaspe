package com.bruno13palhano.authentication.core

import com.bruno13palhano.model.User


interface UserAuthentication {
    fun createUser(user: User, successfulCallback: () -> Unit, failedCallback: () -> Unit)
    fun login(email: String, password: String, successfulCallback: () -> Unit, failedCallback: () -> Unit)
    fun logout()
    fun isUserAuthenticated(): Boolean
    fun getCurrentUser(): User
    fun updateUser(user: User)
}