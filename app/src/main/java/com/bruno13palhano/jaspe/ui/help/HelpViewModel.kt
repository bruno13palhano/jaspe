package com.bruno13palhano.jaspe.ui.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.repository.di.DefaultContactInfoRepository
import com.bruno13palhano.repository.repository.contact.ContactInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(
    @DefaultContactInfoRepository
    private val contactInfoRepository: ContactInfoRepository
) : ViewModel() {
//    private val _instagramInfo = MutableStateFlow("")
//    val instagramInfo = _instagramInfo.asStateFlow()
    val instagramInfo: StateFlow<String> = contactInfoRepository.getContactInfo(1L)
            .map { it.contactInstagram }
            .stateIn(
                initialValue = "",
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )

//    private val _whatsAppInfo = MutableStateFlow("")
//    val whatsAppInfo = _whatsAppInfo.asStateFlow()
    val whatsAppInfo: StateFlow<String> = contactInfoRepository.getContactInfo(1L)
            .map { it.contactWhatsApp }
            .stateIn(
                initialValue = "",
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )
//    init {
//        viewModelScope.launch {
//            try {
//                contactInfoRepository.getContactInfo(1L).collect {
//                    _instagramInfo.value = it.contactInstagram
//                    _whatsAppInfo.value = it.contactWhatsApp
//                }
//            } catch (ignored: Exception) {}
//        }
//    }
}