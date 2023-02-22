package com.bruno13palhano.repository


import com.bruno13palhano.model.User
import com.bruno13palhano.repository.dao.UserDao
import com.bruno13palhano.repository.external.UserRepository
import com.bruno13palhano.repository.external.UserRepositoryImpl
import com.bruno13palhano.repository.model.asUserRep
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.coroutines.suspendCoroutine


class UserRepositoryImplTest {
    private lateinit var user: User
    private lateinit var currentUser: User
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        user = User(
            uid = "Abcd0123",
            username = "Bruno Barbosa",
            email = "bruno13palhano@gmail.com",
            password = "",
            urlPhoto = "https://www.somephotourl.com"
        )
        repository = mock<UserRepositoryImpl>()
        currentUser = User(
            uid = "uid",
            username = "username",
            email = "emial",
            password = "password",
            urlPhoto = "urlPhoto"
        )
    }

    @Test
    fun getUserWithEmptyUid_shouldReturnDefaultUser() = runBlocking {
        whenever(repository.getUserByUid(anyString()))
            .thenReturn(
                flow {
                    emit(User())
                }
            )

        repository.getUserByUid("").collect{
             currentUser = it
        }
        Assert.assertEquals(User(), currentUser)
    }

    @Test
    fun getUserWithNonexistentUid_shouldReturnDefaultUser() = runBlocking {
        whenever(repository.getUserByUid(anyString()))
            .thenReturn(
                flow {
                    emit(User())
                }
            )

        repository.getUserByUid(anyString()).collect {
            currentUser = it
        }

        Assert.assertNotEquals(user.uid, currentUser.uid)
    }

    @Test
    fun getUserWithExistentUid_shouldReturnUser() = runBlocking {
        whenever(repository.getUserByUid(user.uid))
            .thenReturn(
                flow {
                    emit(user)
                }
            )

        repository.getUserByUid(user.uid).collect {
            currentUser = it
        }

        Assert.assertEquals(user, currentUser)
    }
}