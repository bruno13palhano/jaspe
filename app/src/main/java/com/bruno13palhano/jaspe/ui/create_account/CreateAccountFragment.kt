package com.bruno13palhano.jaspe.ui.create_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateAccountFragment : Fragment(), AccountView {
    private val viewModel: CreateAccountViewModel by viewModels()
    private lateinit var loginProgress: FrameLayout

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
        loginProgress = view.findViewById(R.id.login_progress)

        createAccountButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

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

        back.setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    override fun onSuccess() {
        CreateAccountSimpleStateHolder.setDrawerEnable(activity)
        CreateAccountSimpleStateHolder.navigateToHome(findNavController())
    }

    override fun onFail() {
        loginProgress.visibility = GONE
        Toast.makeText(requireContext(), R.string.authentication_failed_label,
            Toast.LENGTH_SHORT).show()
    }

    override fun onLoading() {
        loginProgress.visibility = VISIBLE
    }
}