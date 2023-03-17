package com.bruno13palhano.jaspe.ui.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentHelpBinding
import com.bruno13palhano.jaspe.ui.common.openInstagram
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HelpFragment : Fragment() {
    private var whatsApp = ""
    private val viewModel: HelpViewModel by viewModels()
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        val view = binding.root

        var instagram = ""

        binding.visitUs.setOnClickListener {
           openInstagram(requireActivity(), instagram)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.instagramInfo.collect {
                    instagram = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.whatsAppInfo.collect {
                    whatsApp = it
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarHelp.inflateMenu(R.menu.menu_toolbar)
        binding.toolbarHelp.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarHelp.title = getString(R.string.help_label)

        binding.toolbarHelp.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.whatsappChat -> {
                    openWhatsApp(requireContext(), whatsApp, "")
                    true
                }
                else -> false
            }
        }

        binding.toolbarHelp.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}