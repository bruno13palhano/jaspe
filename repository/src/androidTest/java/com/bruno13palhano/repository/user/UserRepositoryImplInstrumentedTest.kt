package com.bruno13palhano.repository.user

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.bruno13palhano.model.User
import com.bruno13palhano.repository.database.JaspeDatabase
import com.bruno13palhano.repository.user.UserFactory.createUser
import com.bruno13palhano.repository.database.dao.UserDao
import com.bruno13palhano.repository.repository.user.UserRepository
import com.bruno13palhano.repository.repository.user.UserRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserRepositoryImplInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var jaspeDatabase: JaspeDatabase
    private lateinit var userDao: UserDao
    private lateinit var user: User
    private lateinit var repository: UserRepository

    @Before
    fun initDB() {
        jaspeDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            JaspeDatabase::class.java
        ).build()
        userDao = jaspeDatabase.userDao
        user = createUser()
        repository = UserRepositoryImpl(userDao)
    }

    @After
    fun closeDB() {
        jaspeDatabase.close()
    }

    @Test
    fun insertUser_shouldReturnTheUserInFlow() = runBlocking {
        repository.insertUser(user)
        repository.getUserByUid(user.uid).test {
            val currentUser = awaitItem()

            Assert.assertEquals(user, currentUser)
            cancel()
        }
    }

    @Test
    fun updateUsername_shouldReturnTheUserWithNewUsernameInFlow() = runBlocking {
        val newUsername = UserFactory.makeRandomString()
        repository.insertUser(user)
        repository.updateUsername(newUsername, user.uid)

        repository.getUserByUid(user.uid).test {
            val currentUser = awaitItem()

            Assert.assertEquals(currentUser.username, newUsername)
            cancel()
        }
    }

    @Test
    fun updateUrlPhoto_shouldReturnTheUserWithNewUrlPhotoInFlow() = runBlocking {
        val newUrlPhoto = UserFactory.makeRandomString()
        repository.insertUser(user)
        repository.updateUserUrlPhoto(newUrlPhoto, user.uid)

        repository.getUserByUid(user.uid).test {
            val currentUser = awaitItem()

            Assert.assertEquals(currentUser.urlPhoto, newUrlPhoto)
            cancel()
        }
    }

    @Test(expected = Exception::class)
    fun getUserByUid_withEmptyUid_shouldThrowException(): Unit = runBlocking {
        repository.getUserByUid("")
    }

    @Test(expected = Exception::class)
    fun getUserByUid_withNullUid_shouldThrowException(): Unit = runBlocking {
        repository.getUserByUid(null)
    }
}