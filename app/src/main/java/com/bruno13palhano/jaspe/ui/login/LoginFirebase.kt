package com.bruno13palhano.jaspe.ui.login

import com.bruno13palhano.authentication.core.UserAuthentication

class LoginFirebase(
    private val authentication: UserAuthentication
) : Login {
    override fun login(
        email: String,
        password: String,
        callback: Login.LoginCallback
    ) {
        if (isUserValid(email, password)) {
            authentication.login(
                email = email,
                password = password,
                successfulCallback = {
                    callback.onSuccess()
                },
                failedCallback = {
                    callback.onFail()
                }
            )
        } else {
            callback.onFail()
        }
    }

    private fun isUserValid(email: String, password: String): Boolean =
        (email != "") && (password != "")
}