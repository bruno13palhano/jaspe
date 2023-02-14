package com.bruno13palhano.jaspe.ui.login

internal interface LoginView {
    fun onLoginSuccess()
    fun onLoginError()
    fun onLoginLoading()
}