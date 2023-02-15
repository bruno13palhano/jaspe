package com.bruno13palhano.jaspe.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import coil.load
import com.bruno13palhano.authentication.core.AuthenticationFactory
import com.bruno13palhano.authentication.core.UserAuthentication
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.User
import com.google.android.material.appbar.MaterialToolbar

class AccountFragment : Fragment() {
    private lateinit var authentication: UserAuthentication
    private lateinit var userProfileImage: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        userProfileImage = view.findViewById(R.id.profile_image)
        usernameTextView = view.findViewById(R.id.username)
        emailTextView = view.findViewById(R.id.email)

        authentication = AuthenticationFactory().createUserFirebase()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_account)
        toolbar.inflateMenu(R.menu.menu_toolbar_account)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.account_label)

        val user = authentication.getCurrentUser()

        if (isUserAuthenticated(user.uid)) {
            setUserView(user)
        } else {
            navigateToLogin()
        }

        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout -> {
                    authentication.logout()
                    navigateToHome()
                    true
                }
                else -> false
            }
        }

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUserView(user: User) {
        userProfileImage.load(user.urlPhoto)
        usernameTextView.text = user.username
        emailTextView.text = user.email
    }

    private fun isUserAuthenticated(uid: String) =
        uid != ""

    private fun navigateToLogin() {
        findNavController().navigate(com.bruno13palhano.jaspe.ui.account.AccountFragmentDirections.actionAccountToLogin())
    }

    private fun navigateToHome() {
        findNavController().navigate(com.bruno13palhano.jaspe.ui.account.AccountFragmentDirections.actionToHome())
    }
}