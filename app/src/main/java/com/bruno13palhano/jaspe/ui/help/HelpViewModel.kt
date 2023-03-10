package com.bruno13palhano.jaspe.ui.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.repository.di.DefaultContactInfoRepository
import com.bruno13palhano.repository.repository.ContactInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(
    @DefaultContactInfoRepository
    private val contactInfoRepository: ContactInfoRepository
) : ViewModel() {
    private val _instagramInfo = MutableStateFlow("")
    val instagramInfo = _instagramInfo.asStateFlow()
    private val _whatsAppInfo = MutableStateFlow("")
    val whatsAppInfo = _whatsAppInfo.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                contactInfoRepository.getContactInfo(1L).collect {
                    _instagramInfo.value = it.contactInstagram
                    _whatsAppInfo.value = it.contactWhatsApp
                }
            } catch (ignored: Exception) {}
        }
    }
}