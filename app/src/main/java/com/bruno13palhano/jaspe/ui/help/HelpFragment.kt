package com.bruno13palhano.jaspe.ui.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.common.openInstagram
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class HelpFragment : Fragment() {
    private var whatsApp = ""
    private val viewModel: HelpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        val visitUs = view.findViewById<AppCompatTextView>(R.id.visit_us)

        var instagram = ""

        visitUs.setOnClickListener {
           openInstagram(requireActivity(), instagram)
        }

        lifecycle.coroutineScope.launch {
            viewModel.instagramInfo.collect {
                instagram = it
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.whatsAppInfo.collect {
                whatsApp = it
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
                    openWhatsApp(requireContext(), whatsApp, "")
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