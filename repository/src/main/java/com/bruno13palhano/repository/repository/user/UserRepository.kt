package com.bruno13palhano.repository.repository.user

import com.bruno13palhano.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun updateUsername(newUsername: String, userUid: String)
    suspend fun updateUserUrlPhoto(newUrlPhoto: String, userUid: String)
    fun getUserByUid(userUid: String?): Flow<User>
}