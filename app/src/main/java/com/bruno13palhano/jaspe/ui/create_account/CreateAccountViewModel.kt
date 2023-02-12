package com.bruno13palhano.jaspe.ui.create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.User
import com.bruno13palhano.repository.external.UserRepository
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }
}