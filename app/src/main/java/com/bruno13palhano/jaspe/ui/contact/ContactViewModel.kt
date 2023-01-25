package com.bruno13palhano.jaspe.ui.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.repository.ContactInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel(
    private val contactInfoRepository: ContactInfoRepository
) : ViewModel() {

    private val _contactWhatsapp = MutableStateFlow("")
    val contactWhatsapp = _contactWhatsapp.asStateFlow()

    private val _contactInstagram = MutableStateFlow("")
    val contactInstagram = _contactInstagram.asStateFlow()

    private val _contactEmail = MutableStateFlow("")
    val contactEmail = _contactEmail.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                contactInfoRepository.getContactInfo(1L).collect() {
                    _contactWhatsapp.value = it.contactWhatsApp
                    _contactInstagram.value = it.contactInstagram
                    _contactEmail.value = it.contactEmail
                }
            } catch (ignored: Exception) {}
        }
    }
}