package com.bruno13palhano.jaspe.ui.account

import app.cash.turbine.test
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.jaspe.ui.home.ModelFactory.makeUser
import com.bruno13palhano.model.User
import com.bruno13palhano.repository.external.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AccountViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var authentication: UserAuthentication

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var viewModel: AccountViewModel

    private lateinit var user: User

    @Before
    fun setUp() {
        user = makeUser()

        doReturn(flow { emit(user) }).`when`(userRepository).getUserByUid(user.uid)

        doReturn(user).`when`(authentication).getCurrentUser()

        viewModel = AccountViewModel(authentication, userRepository)
    }

    @Test
    fun username_shouldReturnUsernameInFlow() = runTest {
        viewModel.username.test {
            val currentUsername = awaitItem()

            Assert.assertEquals(user.username, currentUsername)
            cancel()
        }
    }

    @Test
    fun userEmail_shouldReturnUserEmailInFlow() = runTest {
        viewModel.userEmail.test {
            val currentUserEmail = awaitItem()

            Assert.assertEquals(user.email, currentUserEmail)
            cancel()
        }
    }

    @Test
    fun userUrlPhoto_shouldReturnUserUrlPhotoInFlow() = runTest {
        viewModel.userUrlPhoto.test {
            val currentUserUrlPhoto = awaitItem()

            Assert.assertEquals(user.urlPhoto, currentUserUrlPhoto)
            cancel()
        }
    }

    @Test
    fun isUserAuthenticated_withEmptyUid_shouldReturnFalse() = runTest {
        val currentUser = makeUser("")

        doReturn(flow { emit(currentUser) }).`when`(userRepository).getUserByUid(currentUser.uid)
        doReturn(currentUser).`when`(authentication).getCurrentUser()

        viewModel = AccountViewModel(authentication, userRepository)

        val currentUserIsAuthenticated = viewModel.isUserAuthenticated()

        Assert.assertFalse(currentUserIsAuthenticated)
    }

    @Test
    fun isUserAuthenticated_withValidUid_shouldReturnTrue() = runTest {
        val currentUserIsAuthenticated = viewModel.isUserAuthenticated()

        Assert.assertTrue(currentUserIsAuthenticated)
    }
}