package com.example.network.model

import com.bruno13palhano.model.ContactInfo
import com.squareup.moshi.Json

internal data class ContactInfoNet(
    @Json(name = "contactWhatsApp")
    val contactWhatsApp: String,

    @Json(name = "contactInstagram")
    val contactInstagram: String,

    @Json(name = "contactEmail")
    val contactEmail: String
)

internal fun ContactInfoNet.asContactInfo() = ContactInfo (
    contactWhatsApp = contactWhatsApp,
    contactInstagram = contactInstagram,
    contactEmail = contactEmail
)
