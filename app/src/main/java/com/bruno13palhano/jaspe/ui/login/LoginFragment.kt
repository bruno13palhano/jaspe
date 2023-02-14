package com.bruno13palhano.jaspe.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.DrawerLock
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginFragment : Fragment(), LoginView {
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val loginButton = view.findViewById<MaterialButton>(R.id.login_button)
        val createAccount = view.findViewById<TextView>(R.id.create_account)
        val closeLogin = view.findViewById<ImageView>(R.id.close_login)

        viewModel = ViewModelFactory(requireContext(), this@LoginFragment)
            .createLoginViewModel()

        emailEditText = view.findViewById(R.id.email)
        passwordEditText = view.findViewById(R.id.password)

        loginButton.setOnClickListener {
            viewModel.login(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        createAccount.setOnClickListener {
            navigateToCreateAccount()
        }

        closeLogin.setOnClickListener {
            onCloseClicked()
        }

        setDrawerEnable(false)

        lifecycle.coroutineScope.launch {
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
                }
            }
        }

        return view
    }


    override fun onLoginSuccess() {
        setDrawerEnable(true)
        navigateToHome()
    }

    override fun onLoginError() {
        Toast.makeText(
            context, getString(R.string.invalid_login_params), Toast.LENGTH_SHORT).show()
    }

    override fun onLoginLoading() {
        println("Loading")
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isUserAuthenticated()) {
            findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
            setDrawerEnable(true)
        }
    }

    private fun setDrawerEnable(enabled: Boolean) {
        ((activity as DrawerLock)).setDrawerEnable(enabled)
    }

    private fun onCloseClicked() {
        setDrawerEnable(true)
        navigateToHome()
    }

    private fun navigateToHome() {
        findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
    }

    private fun navigateToCreateAccount() {
        findNavController().navigate(LoginFragmentDirections.actionLoginToCreateAccount())
    }
}