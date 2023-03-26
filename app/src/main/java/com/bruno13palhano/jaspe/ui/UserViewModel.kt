package com.bruno13palhano.jaspe.ui

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.authentication.core.DefaultUserFirebase
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.model.User
import com.bruno13palhano.repository.di.DefaultUserRepository
import com.bruno13palhano.repository.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    @DefaultUserRepository private val userRepository: UserRepository,
    @DefaultUserFirebase private val authentication: UserAuthentication
) : ViewModel() {

    private val _loginStatus = MutableStateFlow<LoginStatus>(LoginStatus.Default)
    val loginStatus = _loginStatus.asStateFlow()

//    private var _uiState = MutableStateFlow(User())
//    val uiState = _uiState.asStateFlow()

//    init {
//        viewModelScope.launch {
//            userRepository.getUserByUidStream(authentication.getCurrentUser().uid).collect { user ->
//                _uiState.value = user
//                println("valor do usuário na viewModel: $user")
//            }
//        }

//    }

    val uiState = userRepository.getUserByUidStream(authentication.getCurrentUser().uid)
        .stateIn(
            initialValue = User(),
            scope = viewModelScope,
            started = WhileSubscribed(5_000)
        )

    suspend fun isUserAuthenticated(): Boolean {
        return if (authentication.isUserAuthenticated()) {
            val user = authentication.getCurrentUser()
            viewModelScope.launch {
                userRepository.insertUser(user)
            }
            true
        } else {
            false
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        loading()
        if (isUserValid(email, password)) {
            authentication.login(
                email = email,
                password = password,
                successfulCallback = {
                    _loginStatus.value = LoginStatus.Success
                    viewModelScope.launch {
                        println("usuátio corrente no login: ${authentication.getCurrentUser()}")
                        userRepository.insertUser(authentication.getCurrentUser())
                    }
                },
                failedCallback = {
                    _loginStatus.value = LoginStatus.Error
                }
            )
        }
    }

    fun createAccount(
        username: String,
        email: String,
        password: String,
    ) {
        loading()
        val user = User(
            username = username,
            email = email,
            password = password
        )
        if (isUserValid(user)) {
            authentication.createUser(
                user = user,
                successfulCallback = {
                    viewModelScope.launch {
                        userRepository.insertUser(
                            User(
                                uid = it,
                                username = user.username,
                                email = user.email
                            )
                        )
                    }
                    _loginStatus.value = LoginStatus.Success
                },
                failedCallback = {
                    _loginStatus.value = LoginStatus.Error
                }
            )
        } else {
            _loginStatus.value = LoginStatus.Error
        }
    }

    fun updateUserUrlPhoto(
        photo: Bitmap,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        authentication.updateUserUrlPhoto(
            photo = photo,
            onSuccess = { newPhotoUrl, userUid ->
                updateUserUrlPhotoInDB(newPhotoUrl, userUid)
                onSuccess()
            },
            onFail = {
                onFail()
            }
        )
    }

    fun updateUsername(
        username: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        authentication.updateUsername(
            username = username,
            onSuccess = { newUsername, userUid ->
                updateUsernameInDB(newUsername, userUid)
                onSuccess()
            },
            onFail = {
                onFail()
            }
        )
    }

    fun logout() {
        authentication.logout()
    }

    private fun updateUsernameInDB(newUsername: String, userUid: String) {
        viewModelScope.launch {
            userRepository.updateUsername(newUsername, userUid)
        }
    }

    private fun updateUserUrlPhotoInDB(newUrlPhoto: String, userUid: String) {
        viewModelScope.launch {
            userRepository.updateUserUrlPhoto(newUrlPhoto, userUid)
        }
    }

    private fun isUserValid(user: User): Boolean =
        user.username != "" && user.email != "" && user.password != ""

    private fun isUserValid(email: String, password: String): Boolean =
        email != "" && password != ""

    private fun loading() {
        _loginStatus.value = LoginStatus.Loading
    }

    sealed class LoginStatus {
        object Loading: LoginStatus()
        object Error: LoginStatus()
        object Success: LoginStatus()
        object Default: LoginStatus()
    }
}