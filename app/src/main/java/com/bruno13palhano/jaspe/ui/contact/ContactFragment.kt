package com.bruno13palhano.jaspe.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.ContactInfo
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class ContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        val whatsAppButton = view.findViewById<MaterialButton>(R.id.whatsApp_button)
        val instagramButton = view.findViewById<MaterialButton>(R.id.instagram_button)
        val gmailButton = view.findViewById<MaterialButton>(R.id.gmail_button)

        whatsAppButton.setOnClickListener {
            openWhatsApp()
        }

        instagramButton.setOnClickListener {
            openInstagram()
        }

        gmailButton.setOnClickListener {
            openEmail()
        }

        return view
    }

    private fun openWhatsApp() {
        try {
            val url = ContactInfo.WHATSAPP.value
            val whatsAppIntent = Intent(Intent.ACTION_VIEW)
            whatsAppIntent.`package` = "com.whatsapp"
            whatsAppIntent.data = Uri.parse(url)
            startActivity(whatsAppIntent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), R.string.contact_whatsapp_error_label, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun openInstagram() {
        try {
            val url = ContactInfo.INSTAGRAM.value
            val instagramIntent = Intent(Intent.ACTION_VIEW)
            instagramIntent.data = Uri.parse(url)
            instagramIntent.`package` = "com.instagram.android"
            startActivity(instagramIntent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), R.string.contact_instagram_error_label, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun openEmail() {
        try {
            val emailIntent = Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", ContactInfo.EMAIL.value, null))
            startActivity(Intent.createChooser(emailIntent, ""))
        } catch (e: Exception) {
            Toast.makeText(requireContext(), R.string.contact_gmail_error_label, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_contact)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.contact_category_label)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}