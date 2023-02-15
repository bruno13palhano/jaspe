package com.bruno13palhano.jaspe.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val authentication: UserAuthentication
) : ViewModel() {
    private val _currentUser = MutableStateFlow(User())

    private val _userUrlPhoto = MutableStateFlow("")
    val userUrlPhoto = _userUrlPhoto.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()

    init {
        viewModelScope.launch {
            _currentUser.value = authentication.getCurrentUser()
            _username.value = _currentUser.value.username
            _userEmail.value = _currentUser.value.email
            _userUrlPhoto.value = _currentUser.value.urlPhoto
        }
    }

    fun isUserAuthenticated(): Boolean = _currentUser.value.uid != ""

    fun logout() = authentication.logout()
}