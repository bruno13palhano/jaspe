package com.bruno13palhano.jaspe.ui.login

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
import com.bruno13palhano.jaspe.databinding.FragmentLoginBinding
import com.bruno13palhano.jaspe.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(), LoginView {
    private val userViewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.loginButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                userViewModel.login(
                    email = binding.email.text.toString(),
                    password = binding.password.text.toString()
                )
            }
        }

        binding.createAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginToCreateAccount())
        }

        binding.closeLogin.setOnClickListener {
            setDrawerEnable(true)
            findNavController().apply {
                popBackStack(R.id.homeFragment, inclusive = true, saveState = true)
                navigate(R.id.action_to_home)
            }
        }

        setDrawerEnable(false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    userViewModel.loginStatus.collect {
                        when (it) {
                            UserViewModel.LoginStatus.Loading -> {
                                onLoginLoading()
                            }
                            UserViewModel.LoginStatus.Success -> {
                                onLoginSuccess()
                            }
                            UserViewModel.LoginStatus.Error -> {
                                onLoginError()
                            }
                            UserViewModel.LoginStatus.Default -> {

                            }
                        }
                    }
                }
            }
        }

        return view
    }

    override fun onLoginSuccess() {
        setDrawerEnable(true)
        userViewModel.restoreLoginStatus()
        findNavController().apply {
            popBackStack(R.id.homeFragment, inclusive = true, saveState = true)
            navigate(R.id.action_to_home)
        }
    }

    override fun onLoginError() {
        binding.loginProgress.visibility = GONE
        Toast.makeText(
            context, getString(R.string.invalid_login_params), Toast.LENGTH_SHORT).show()
    }

    override fun onLoginLoading() {
        binding.loginProgress.visibility = VISIBLE
    }

    override fun onStart() {
        super.onStart()
        if (userViewModel.isUserAuthenticated()) {
            onLoginSuccess()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDrawerEnable(enabled: Boolean) {
        ((activity as DrawerLock)).setDrawerEnable(enabled)
    }
}