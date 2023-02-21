package com.bruno13palhano.repository.external

import com.bruno13palhano.model.User
import com.bruno13palhano.repository.dao.UserDao
import com.bruno13palhano.repository.model.asUser
import com.bruno13palhano.repository.model.asUserRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: User) {
        userDao.insert(user.asUserRep())
    }

    override suspend fun updateUsername(newUsername: String, userUid: String) {
        userDao.updateUsername(newUsername, userUid)
    }

    override suspend fun updateUserUrlPhoto(newUrlPhoto: String, userUid: String) {
        userDao.updateUserUrlPhoto(newUrlPhoto, userUid)
    }

    override fun getUserByUid(userUid: String?): Flow<User> {
        return if (isUserUidValid(userUid)) {
            userDao.getUserByUid(userUid).map {
                it.asUser()
            }
        } else {
            flow {
                User()
            }
        }
    }

    private fun isUserUidValid(userUid: String?): Boolean =
        userUid != null && userUid != ""

}