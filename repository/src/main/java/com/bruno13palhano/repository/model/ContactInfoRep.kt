package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bruno13palhano.model.ContactInfo

@Entity(tableName = "contact_table")
internal data class ContactInfoRep(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    val contactId: Long,

    @ColumnInfo(name = "contact_whatsapp")
    val contactWhatsApp: String,

    @ColumnInfo(name = "contact_instagram")
    val contactInstagram: String,

    @ColumnInfo(name = "contact_email")
    val contactEmail: String
)

internal fun ContactInfoRep.asContactInfo() = ContactInfo(
    contactWhatsApp = contactWhatsApp,
    contactInstagram = contactInstagram,
    contactEmail = contactEmail
)

internal fun ContactInfo.asContactInfoRep() = ContactInfoRep(
    contactId = 0L,
    contactWhatsApp = contactWhatsApp,
    contactInstagram = contactInstagram,
    contactEmail = contactEmail
)