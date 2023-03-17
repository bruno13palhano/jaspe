package com.bruno13palhano.jaspe.ui.create_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.DrawerLock
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentCreateAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateAccountFragment : Fragment(), AccountView {
    private val viewModel: CreateAccountViewModel by viewModels()
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.create.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            viewModel.createAccount(
                username = username,
                email = email,
                password = password
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createAccountStatus.collect {
                    when (it) {
                        CreateAccountStatus.Success -> {
                            onSuccess()
                        }
                        CreateAccountStatus.Error -> {
                            onFail()
                        }
                        CreateAccountStatus.Loading -> {
                            onLoading()
                        }
                        CreateAccountStatus.Default -> {

                        }
                    }
                }
            }
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    override fun onSuccess() {
        setDrawerEnable()
        navigateToHome()
    }

    override fun onFail() {
        binding.loginProgress.visibility = GONE
        Toast.makeText(requireContext(), R.string.authentication_failed_label,
            Toast.LENGTH_SHORT).show()
    }

    override fun onLoading() {
        binding.loginProgress.visibility = VISIBLE
    }

    private fun setDrawerEnable() {
        ((activity as DrawerLock)).setDrawerEnable(true)
    }

    private fun navigateToHome() {
        findNavController().navigate(CreateAccountFragmentDirections
            .actionCreateAccountToHome())
    }
}