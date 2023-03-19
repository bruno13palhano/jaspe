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

    val uiState = contactInfoRepository.getContactInfo(1L)
        .map {
            HelpUiState(
                instagramInfo = it.contactInstagram,
                whatsAppInfo = it.contactWhatsApp
            )
        }
        .stateIn(
            initialValue = HelpUiState(),
            scope = viewModelScope,
            started = WhileSubscribed(5_000)
        )
}