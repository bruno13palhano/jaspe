package com.bruno13palhano.jaspe.ui.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.jaspe.ui.common.openEmail
import com.bruno13palhano.jaspe.ui.common.openInstagram
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
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
            openWhatsApp(this@ContactFragment.requireContext(), contactWhatsapp)
        }

        instagramButton.setOnClickListener {
            openInstagram(this@ContactFragment.requireContext(), contactInstagram)
        }

        gmailButton.setOnClickListener {
            openEmail(this@ContactFragment.requireContext(), contactEmail)
        }

        return view
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