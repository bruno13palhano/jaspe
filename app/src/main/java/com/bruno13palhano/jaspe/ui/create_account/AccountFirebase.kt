package com.bruno13palhano.jaspe.ui.create_account

import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.model.User

class AccountFirebase(
    private val authentication: UserAuthentication
) : Account {
    override fun createUser(
        username: String,
        email: String,
        password: String,
        callback: Account.CreateAccountCallback
    ) {
        val user = User(
            username = username,
            email = email,
            password = password
        )
        if (isUserValid(user)) {
            authentication.createUser(
                user = user,
                successfulCallback = {
                    val newUser = User(
                        uid = authentication.getCurrentUser().uid,
                        username = user.username,
                        email = user.email,
                    )

                    callback.onSuccess(newUser)
                },
                failedCallback = {
                    callback.onFail()
                }
            )
        } else {
            callback.onFail()
        }
    }

    private fun isUserValid(user: User): Boolean {
        return (user.username != "") && (user.email != "") && (user.password != "")
    }
}