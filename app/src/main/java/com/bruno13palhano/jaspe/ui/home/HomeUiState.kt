package com.bruno13palhano.jaspe.ui.home

import com.bruno13palhano.model.ContactInfo

data class HomeUiState (
    val contactInfo: ContactInfo = ContactInfo(),
    val username: String = "",
    val profileUrlPhoto: String = "",
    val notificationCount: Int = 0
)