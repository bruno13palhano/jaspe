package com.bruno13palhano.jaspe.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.authentication.core.UserFirebase
import com.bruno13palhano.jaspe.DrawerLock
import com.bruno13palhano.jaspe.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var authentication: UserAuthentication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val loginButton = view.findViewById<MaterialButton>(R.id.login_button)
        val createAccount = view.findViewById<TextView>(R.id.create_account)
        val closeLogin = view.findViewById<ImageView>(R.id.close_login)

        authentication = UserFirebase()
        emailEditText = view.findViewById(R.id.email)
        passwordEditText = view.findViewById(R.id.password)

        loginButton.setOnClickListener {
            onLoginButtonClick()
        }

        createAccount.setOnClickListener {
            navigateToCreateAccount()
        }

        closeLogin.setOnClickListener {
            onCloseClicked()
        }

        setDrawerEnable(false)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (authentication.isUserAuthenticated()) {
            findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
            setDrawerEnable(true)
        }
    }

    private fun setDrawerEnable(enabled: Boolean) {
        ((activity as DrawerLock)).setDrawerEnable(enabled)
    }

    private fun onLoginButtonClick() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (isParamsValid(email, password)) {
            login(email, password)
            setDrawerEnable(true)
        } else {
            Toast.makeText(
                context, getString(R.string.invalid_login_params), Toast.LENGTH_SHORT).show()
        }
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

    private fun isParamsValid(email: String, password: String): Boolean =
        email != "" && password != ""

    private fun login(email: String, password: String) {
        authentication.login(
            email = email,
            password = password,
            successfulCallback = { navigateToHome() },
            failedCallback = {
                Toast.makeText(
                        context, getString(R.string.invalid_login_params), Toast.LENGTH_SHORT).show()
            }
        )
    }
}