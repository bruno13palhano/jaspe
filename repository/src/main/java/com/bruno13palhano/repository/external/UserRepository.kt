package com.bruno13palhano.repository.external

import com.bruno13palhano.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User)
    fun getUserByUid(userUid: String): Flow<User>
}