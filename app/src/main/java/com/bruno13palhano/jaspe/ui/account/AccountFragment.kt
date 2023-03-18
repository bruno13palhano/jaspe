package com.bruno13palhano.jaspe.ui.account

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private val viewModel: AccountViewModel by viewModels()
    private lateinit var photoObserver: ProfilePhotoLifecycleObserver
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val view = binding.root

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
        binding.toolbarAccount.inflateMenu(R.menu.menu_toolbar_account)
        binding.toolbarAccount.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarAccount.title = getString(R.string.account_label)

        binding.profileImage.setOnClickListener {
            photoObserver.selectImage()
        }

        binding.username.setOnClickListener {
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
                        binding.username.text = it
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.userEmail.collect {
                        binding.email.text = it
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.userUrlPhoto.collect {
                        binding.profileImage.load(it)
                    }
                }
            }
        } else {
            findNavController().navigate(AccountFragmentDirections.actionAccountToLogin())
        }

        binding.toolbarAccount.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout -> {
                    viewModel.logout()
                    findNavController().navigate(AccountFragmentDirections.actionToHome())

                    true
                }
                else -> false
            }
        }

        binding.toolbarAccount.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}