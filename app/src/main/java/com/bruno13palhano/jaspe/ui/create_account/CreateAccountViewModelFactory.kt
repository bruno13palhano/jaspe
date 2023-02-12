package com.bruno13palhano.jaspe.ui.create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.UserRepository

class CreateAccountViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateAccountViewModel::class.java)) {
            return CreateAccountViewModel(userRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}