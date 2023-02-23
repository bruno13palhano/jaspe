package com.bruno13palhano.repository

import com.bruno13palhano.model.User
import java.util.UUID

object UserFactory {

    fun createUser(): User {
        return User(
            uid = makeRandomString(),
            username = makeRandomString(),
            email = makeRandomString(),
            urlPhoto = makeRandomString()
        )
    }

    private fun makeRandomString() = UUID.randomUUID().toString()
}