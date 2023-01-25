package com.bruno13palhano.jaspe.ui.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.ContactInfoRepository

class ContactViewModelFactory(
    private val repository: ContactInfoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}