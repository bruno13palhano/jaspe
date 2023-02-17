package com.bruno13palhano.jaspe.ui.account

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.repository.external.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val authentication: UserAuthentication,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userUrlPhoto = MutableStateFlow("")
    val userUrlPhoto = _userUrlPhoto.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                userRepository.getUserByUid(authentication.getCurrentUser().uid).collect {
                    _username.value = it.username
                    _userEmail.value = it.email
                    _userUrlPhoto.value = it.urlPhoto
                }
            } catch (ignored: Exception) {}
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

    fun logout() = authentication.logout()
}