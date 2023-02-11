package com.bruno13palhano.jaspe.ui.create_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.authentication.core.UserFirebase
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreateAccountFragment : Fragment() {
    private lateinit var authentication: UserAuthentication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_account, container, false)
        val createAccountButton = view.findViewById<MaterialButton>(R.id.create)
        val back = view.findViewById<TextView>(R.id.back)
        val usernameEditText = view.findViewById<TextInputEditText>(R.id.username)
        val emailEditText = view.findViewById<TextInputEditText>(R.id.email)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.password)

        authentication = UserFirebase()

        createAccountButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isUserParamsValid(username, email, password)) {
                val user = User(
                    username = username,
                    email = email,
                    password = password
                )
                createUser(user)
            }
        }

        back.setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    private fun createUser(user: User) {
        authentication.createUser(
            user = user,
            successfulCallback = {
                navigateToHome()
            },
            failedCallback = {
                Toast.makeText(requireContext(), R.string.authentication_failed_label,
                    Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun isUserParamsValid(username: String, email: String, password: String): Boolean =
        (username != "") && (email != "") && (password != "")

    private fun navigateToHome() {
        findNavController().navigate(
            CreateAccountFragmentDirections.actionCreateAccountToHome())
    }
}