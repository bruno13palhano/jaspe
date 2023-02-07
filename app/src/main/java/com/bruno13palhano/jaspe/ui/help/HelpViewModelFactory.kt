package com.bruno13palhano.jaspe.ui.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno13palhano.repository.external.ContactInfoRepository

class HelpViewModelFactory(
    private val contactInfoRepository: ContactInfoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HelpViewModel::class.java)) {
            return HelpViewModel(contactInfoRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}