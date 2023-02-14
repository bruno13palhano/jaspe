package com.bruno13palhano.jaspe.ui.login

import com.bruno13palhano.model.User

interface Login {
    fun login(email: String, password: String, callback: LoginCallback)

    interface LoginCallback {
        fun onSuccess(user: User)
        fun onFail()
        fun onLoading()
    }
}