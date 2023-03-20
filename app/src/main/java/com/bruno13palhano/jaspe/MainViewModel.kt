package com.bruno13palhano.jaspe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.repository.di.DefaultContactInfoRepository
import com.bruno13palhano.repository.repository.contact.ContactInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @DefaultContactInfoRepository private val contactInfoRepository: ContactInfoRepository,
) : ViewModel() {

    val contactInfo = contactInfoRepository.getContactInfoStream(1L)
        .stateIn(
            initialValue = ContactInfo(),
            scope = viewModelScope,
            started = WhileSubscribed(5_000)
        )
}
