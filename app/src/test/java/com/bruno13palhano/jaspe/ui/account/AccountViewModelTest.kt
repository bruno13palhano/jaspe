package com.bruno13palhano.jaspe.ui.account

import app.cash.turbine.test
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.jaspe.ui.ModelFactory.makeUser
import com.bruno13palhano.model.User
import com.bruno13palhano.repository.repository.user.UserRepository
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

}