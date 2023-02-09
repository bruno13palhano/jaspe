package com.bruno13palhano.jaspe.ui.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.jaspe.ui.common.openInstagram
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.ContactInfo
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class HelpFragment : Fragment() {
    private lateinit  var contactInfo: ContactInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        val visitUs = view.findViewById<AppCompatTextView>(R.id.visit_us)

        contactInfo = ContactInfo()

        visitUs.setOnClickListener {
           openInstagram(requireActivity(), contactInfo.contactInstagram)
        }

        val viewModel = ViewModelFactory(requireContext().applicationContext, this@HelpFragment)
            .createHelpViewModel()

        lifecycle.coroutineScope.launch {
            viewModel.contactInfo.collect {
                contactInfo = it
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_help)
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.help_label)

        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.whatsappChat -> {
                    openWhatsApp(requireContext(), contactInfo.contactWhatsApp, "")
                    true
                }
                else -> false
            }
        }

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}