package com.bruno13palhano.repository.external

import com.bruno13palhano.model.User
import com.bruno13palhano.repository.dao.UserDao
import com.bruno13palhano.repository.model.asUser
import com.bruno13palhano.repository.model.asUserRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: User) {
        userDao.insert(user.asUserRep())
    }

    override fun getUserByUid(userUid: String): Flow<User> {
        return userDao.getUserByUid(userUid).map {
            it.asUser()
        }
    }
}