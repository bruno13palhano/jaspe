package com.bruno13palhano.jaspe.ui.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.repository.repository.user.UserRepository
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class LoginViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

}