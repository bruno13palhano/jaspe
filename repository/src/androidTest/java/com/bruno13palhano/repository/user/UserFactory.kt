package com.bruno13palhano.repository.user

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

    fun makeRandomString() = UUID.randomUUID().toString()
}