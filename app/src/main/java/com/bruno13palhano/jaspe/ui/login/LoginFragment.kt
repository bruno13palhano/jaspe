package com.bruno13palhano.jaspe.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.DrawerLock
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(), LoginView {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.loginButton.setOnClickListener {
            viewModel.login(
                email = binding.email.text.toString(),
                password = binding.password.text.toString()
            )
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

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.loginStatus.collect {
                when (it) {
                    LoginStatus.Loading -> {
                        onLoginLoading()
                    }
                    LoginStatus.Success -> {
                        onLoginSuccess()
                    }
                    LoginStatus.Error -> {
                        onLoginError()
                    }
                    LoginStatus.Default -> {

                    }
                }
            }
        }

        return view
    }

    override fun onLoginSuccess() {
        setDrawerEnable(true)
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
        if (viewModel.isUserAuthenticated()) {
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