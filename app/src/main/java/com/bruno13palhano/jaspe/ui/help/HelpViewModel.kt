package com.bruno13palhano.jaspe.ui.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.repository.external.ContactInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HelpViewModel(
    private val contactInfoRepository: ContactInfoRepository
) : ViewModel() {
    private val _contactInfo = MutableStateFlow(ContactInfo())
    val contactInfo = _contactInfo.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                contactInfoRepository.getContactInfo(1L).collect {
                    _contactInfo.value = it
                }
            } catch (ignored: Exception) {}
        }
    }
}