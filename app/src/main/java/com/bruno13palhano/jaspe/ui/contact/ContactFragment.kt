package com.bruno13palhano.jaspe.ui.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class ContactFragment : Fragment() {
    private var contactWhatsapp = ""
    private var contactInstagram = ""
    private var contactEmail = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        val whatsAppButton = view.findViewById<MaterialButton>(R.id.whatsApp_button)
        val instagramButton = view.findViewById<MaterialButton>(R.id.instagram_button)
        val gmailButton = view.findViewById<MaterialButton>(R.id.gmail_button)

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@ContactFragment).createContactViewModel()
        }

        lifecycle.coroutineScope.launch {
            viewModel?.contactWhatsapp?.collect {
                contactWhatsapp = it
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel?.contactInstagram?.collect {
                contactInstagram = it
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel?.contactEmail?.collect {
                contactEmail = it
            }
        }

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
            val whatsAppIntent = Intent(Intent.ACTION_VIEW)
            whatsAppIntent.`package` = "com.whatsapp"
            whatsAppIntent.data = Uri.parse(
                "https://api.whatsapp.com/send?phone=+${contactWhatsapp}")
            startActivity(whatsAppIntent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), R.string.contact_whatsapp_error_label, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun openInstagram() {
        try {
            val instagramIntent = Intent(Intent.ACTION_VIEW)
            instagramIntent.data = Uri.parse(
                "https://instagram.com/${contactInstagram}")
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
                Uri.fromParts("mailto", contactEmail, null))
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