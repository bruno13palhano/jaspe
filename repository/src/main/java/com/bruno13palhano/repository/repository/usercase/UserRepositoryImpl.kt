package com.bruno13palhano.repository.repository.usercase

import com.bruno13palhano.model.User
import com.bruno13palhano.repository.database.dao.UserDao
import com.bruno13palhano.repository.model.asUser
import com.bruno13palhano.repository.model.asUserRep
import com.bruno13palhano.repository.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRepositoryImpl @Inject constructor(
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
                emit(User())
            }
        }
    }

    private fun isUserUidValid(userUid: String?): Boolean =
        userUid != null && userUid != ""

}