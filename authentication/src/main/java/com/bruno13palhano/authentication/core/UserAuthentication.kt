package com.bruno13palhano.authentication.core

import android.graphics.Bitmap
import com.bruno13palhano.model.User

interface UserAuthentication {
    fun createUser(user: User, successfulCallback: (userUid: String) -> Unit, failedCallback: () -> Unit)
    fun login(email: String,
        password: String, successfulCallback: () -> Unit, failedCallback: () -> Unit)
    fun logout()
    fun isUserAuthenticated(): Boolean
    fun getCurrentUser(): User
    fun updateUserUrlPhoto(photo: Bitmap,
        onSuccess: (newPhotoUrl: String, userUid: String) -> Unit, onFail: () -> Unit)
    fun updateUsername(username: String,
        onSuccess: (newUsername: String, userUid: String) -> Unit, onFail: () -> Unit)
}