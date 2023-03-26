package com.bruno13palhano.jaspe.ui.create_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.DrawerLock
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentCreateAccountBinding
import com.bruno13palhano.jaspe.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateAccountFragment : Fragment(), AccountView {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

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

            userViewModel.createAccount(
                username = username,
                email = email,
                password = password
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.loginStatus.collect {
                    when (it) {
                        UserViewModel.LoginStatus.Success -> {
                            onSuccess()
                        }
                        UserViewModel.LoginStatus.Error -> {
                            onFail()
                        }
                        UserViewModel.LoginStatus.Loading -> {
                            onLoading()
                        }
                        UserViewModel.LoginStatus.Default -> {

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
        userViewModel.restoreLoginStatus()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDrawerEnable() {
        ((activity as DrawerLock)).setDrawerEnable(true)
    }

    private fun navigateToHome() {
        findNavController().apply {
            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
            navigate(R.id.action_to_home)
        }
    }
}