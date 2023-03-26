package com.bruno13palhano.jaspe.ui.account

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentAccountBinding
import com.bruno13palhano.jaspe.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private lateinit var photoObserver: ProfilePhotoLifecycleObserver
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

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
                    userViewModel.updateUserUrlPhoto(
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

        if (userViewModel.isUserAuthenticated()) {
        viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        userViewModel.getUserState().collect {
                            binding.username.text = it.username
                            binding.email.text = it.email
                            binding.profileImage.load(it.urlPhoto)
                        }
                    }
                }
            }
        } else {
            findNavController().navigate(AccountFragmentDirections.actionToLogin())
        }

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
                        userViewModel.updateUsername(
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

        binding.toolbarAccount.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout -> {
                    userViewModel.logout()
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