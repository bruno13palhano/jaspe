package com.bruno13palhano.jaspe.ui.login

import com.bruno13palhano.model.User
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify

class LoginFirebaseTest {

    private val loginFirebase = mock<LoginFirebase>()

    val callback = object : Login.LoginCallback {
        override fun onSuccess(user: User) {
            TODO("Not yet implemented")
        }

        override fun onFail() {
            TODO("Not yet implemented")
        }

    }
}