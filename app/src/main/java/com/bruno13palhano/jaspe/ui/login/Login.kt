package com.bruno13palhano.jaspe.ui.login

interface Login {
    fun login(email: String, password: String, callback: LoginCallback)

    interface LoginCallback {
        fun onSuccess()
        fun onFail()
        fun onLoading()
    }
}