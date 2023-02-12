package com.bruno13palhano.jaspe.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.User
import com.bruno13palhano.repository.external.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun getUserByUid(userUid: String): Flow<User> {
        return userRepository.getUserByUid(userUid)
    }
}