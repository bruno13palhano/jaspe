package com.bruno13palhano.jaspe.ui.account

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.bruno13palhano.jaspe.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private lateinit var userProfileImage: ShapeableImageView
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private val viewModel: AccountViewModel by viewModels()
    private lateinit var photoObserver: ProfilePhotoLifecycleObserver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        userProfileImage = view.findViewById(R.id.profile_image)
        usernameTextView = view.findViewById(R.id.username)
        emailTextView = view.findViewById(R.id.email)

        photoObserver = ProfilePhotoLifecycleObserver(
            registry = requireActivity().activityResultRegistry,
            contentResolver = requireContext().contentResolver,
            photoListener = object : PhotoListener {
                override fun onSuccess(bitmapPhoto: Bitmap) {
                    viewModel.updateUserUrlPhoto(
                        photo = bitmapPhoto,
                        onSuccess = {
                            Toast.makeText(requireContext(),
                                R.string.username_updated_successfully_label,
                                Toast.LENGTH_SHORT).show()
                        },
                        onFail = {
                            Toast.makeText(requireContext(), R.string.server_error_label,
                                Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                override fun onFail() {
                    Toast.makeText(requireContext(), R.string.processing_image_error,
                        Toast.LENGTH_SHORT).show()
                }
            }
        )
        lifecycle.addObserver(photoObserver)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_account)
        toolbar.inflateMenu(R.menu.menu_toolbar_account)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.account_label)

        userProfileImage.setOnClickListener {
            photoObserver.selectImage()
        }

        usernameTextView.setOnClickListener {
            val dialog = UpdateUsernameDialogFragment(object : UpdateUsernameDialogFragment
                .UsernameListener {
                override fun onDialogPositiveClick(newUsername: String) {
                    if (newUsername.isNotEmpty()) {
                        viewModel.updateUsername(
                            username = newUsername,
                            onSuccess = {
                                Toast.makeText(requireContext(),
                                    R.string.username_updated_successfully_label,
                                    Toast.LENGTH_SHORT).show()
                            },
                            onFail = {
                                Toast.makeText(requireContext(), R.string.server_error_label,
                                    Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }

            })
            dialog.show(requireActivity().supportFragmentManager,
                "UpdateUsernameDialogFragment")
        }

        if (viewModel.isUserAuthenticated()) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.username.collect {
                        usernameTextView.text = it
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.userEmail.collect {
                        emailTextView.text = it
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.userUrlPhoto.collect {
                        userProfileImage.load(it)
                    }
                }
            }
        } else {
            findNavController().navigate(AccountFragmentDirections.actionAccountToLogin())
        }

        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout -> {
                    viewModel.logout()
                    findNavController().navigate(AccountFragmentDirections.actionToHome())

                    true
                }
                else -> false
            }
        }

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}