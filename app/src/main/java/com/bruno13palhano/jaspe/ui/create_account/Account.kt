package com.bruno13palhano.jaspe.ui.create_account

import com.bruno13palhano.model.User

interface Account {
    fun createUser(
        username: String,
        email: String,
        password: String,
        callback: CreateAccountCallback
    )

    interface CreateAccountCallback {
        fun onSuccess(newUser: User)
        fun onFail()
    }
}