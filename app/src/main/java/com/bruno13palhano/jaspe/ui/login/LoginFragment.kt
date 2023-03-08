package com.bruno13palhano.jaspe.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.DrawerLock
import com.bruno13palhano.jaspe.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginFragment : Fragment(), LoginView {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var loginProgress: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val loginButton = view.findViewById<MaterialButton>(R.id.login_button)
        val createAccount = view.findViewById<TextView>(R.id.create_account)
        val closeLogin = view.findViewById<ImageView>(R.id.close_login)
        loginProgress = view.findViewById(R.id.login_progress)

        val emailEditText = view.findViewById<TextInputEditText>(R.id.email)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.password)

        loginButton.setOnClickListener {
            viewModel.login(
                email = emailEditText.text.toString(),
                password = passwordEditText.text.toString()
            )
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
                    LoginStatus.Default -> {

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
        loginProgress.visibility = GONE
        Toast.makeText(
            context, getString(R.string.invalid_login_params), Toast.LENGTH_SHORT).show()
    }

    override fun onLoginLoading() {
        loginProgress.visibility = VISIBLE
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isUserAuthenticated()) {
            onLoginSuccess()
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