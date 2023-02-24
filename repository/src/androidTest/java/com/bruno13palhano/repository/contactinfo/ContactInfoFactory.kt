package com.bruno13palhano.repository.contactinfo

import com.bruno13palhano.model.ContactInfo
import java.util.*

object ContactInfoFactory {

    fun createContactInfo(): ContactInfo {
        return ContactInfo(
            contactEmail = makeRandomString(),
            contactInstagram = makeRandomString(),
            contactWhatsApp = makeRandomString()
        )
    }

    private fun makeRandomString() = UUID.randomUUID().toString()
}