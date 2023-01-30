package com.bruno13palhano.jaspe.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.bruno13palhano.jaspe.R

fun openWhatsApp(context: Context, whatsapp: String, urlLink: String) {
    try {
        val whatsAppIntent = Intent(Intent.ACTION_VIEW)
        whatsAppIntent.`package` = "com.whatsapp"
        whatsAppIntent.data = Uri.parse(
            "https://api.whatsapp.com/send?phone=+$whatsapp&text=$urlLink")
        context.startActivity(whatsAppIntent)
    } catch (e: Exception) {
        Toast.makeText(context.applicationContext, R.string.contact_whatsapp_error_label, Toast.LENGTH_SHORT)
            .show()
    }
}

fun openInstagram(context: Context, instagram: String) {
    try {
        val instagramIntent = Intent(Intent.ACTION_VIEW)
        instagramIntent.data = Uri.parse(
            "https://instagram.com/$instagram")
        instagramIntent.`package` = "com.instagram.android"
        context.startActivity(instagramIntent)
    } catch (e: Exception) {
        Toast.makeText(context.applicationContext, R.string.contact_instagram_error_label, Toast.LENGTH_SHORT)
            .show()
    }
}

fun openEmail(context: Context, email: String) {
    try {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", email, null))
        context.startActivity(Intent.createChooser(emailIntent, ""))
    } catch (e: Exception) {
        Toast.makeText(context.applicationContext, R.string.contact_gmail_error_label, Toast.LENGTH_SHORT)
            .show()
    }
}