package com.bruno13palhano.jaspe.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bruno13palhano.jaspe.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val loginButton = view.findViewById<MaterialButton>(R.id.login_button)
        val createAccount = view.findViewById<TextView>(R.id.create_account)
        emailEditText = view.findViewById(R.id.email)
        passwordEditText = view.findViewById(R.id.password)

        auth = Firebase.auth

        loginButton.setOnClickListener {
            onLoginButtonClick()
        }

        createAccount.setOnClickListener {
            println("create account was clicked")
        }

        return view
    }

    private fun onLoginButtonClick() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (isParamsValid(email, password)) {
            login(email, password)
            //call navigateToHome()
        } else {
            Toast.makeText(
                context, getString(R.string.invalid_login_params), Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHome() {
        // TODO: implementar a navegação
    }

    private fun isParamsValid(email: String, password: String): Boolean =
        email != "" && password != ""

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                    println("username: ${user?.displayName}")
                    println("email: ${user?.email}")
                    println("photoUrl: ${user?.photoUrl}")
                } else {
                    Toast.makeText(
                        context, getString(R.string.invalid_login_params), Toast.LENGTH_SHORT).show()
                }
            }
    }
}