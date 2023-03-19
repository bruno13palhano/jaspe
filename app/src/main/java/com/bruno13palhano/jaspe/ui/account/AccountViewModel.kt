package com.bruno13palhano.jaspe.ui.account

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.authentication.core.DefaultUserFirebase
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.repository.di.DefaultUserRepository
import com.bruno13palhano.repository.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    @DefaultUserFirebase
    private val authentication: UserAuthentication,

    @DefaultUserRepository
    private val userRepository: UserRepository
) : ViewModel() {

    val uiState = userRepository.getUserByUid(authentication.getCurrentUser().uid)
        .map {
            AccountUiState(
                username = it.username,
                email = it.email,
                profileImage = it.urlPhoto
            )
        }
        .stateIn(
            initialValue = AccountUiState(),
            scope = viewModelScope,
            started = WhileSubscribed(5_000)
        )

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

    fun isUserAuthenticated(): Boolean = authentication.getCurrentUser().uid != ""

    fun logout() {
        authentication.logout()
    }
}