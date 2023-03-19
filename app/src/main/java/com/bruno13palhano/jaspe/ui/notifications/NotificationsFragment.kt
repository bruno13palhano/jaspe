package com.bruno13palhano.jaspe.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentNotificationsBinding
import com.bruno13palhano.model.NotificationTypes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationsFragment : Fragment() {
    private val viewModel: NotificationsViewModel by viewModels()
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = NotificationsItemAdapter(
            onCloseClick = {
                viewModel.deleteNotification(it)
            },
            onItemClick = {
                navigateTo(NotificationTypes.valueOf(it))
            }
        )
        binding.notificationsList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notifications.collect {
                    adapter.submitList(it)
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarNotifications.inflateMenu(R.menu.menu_toolbar_favorites)
        binding.toolbarNotifications.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarNotifications.title = getString(R.string.notifications_label)

        binding.toolbarNotifications.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteAll -> {
                    lifecycle.coroutineScope.launch {
                        viewModel.notifications.collect { notifications ->
                            viewModel.deleteAllNotifications(notifications)
                        }
                    }
                    true
                } else -> {
                    false
                }
            }
        }

        binding.toolbarNotifications.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateTo(type: NotificationTypes) {
        when (type) {
            NotificationTypes.ANNOUNCEMENT -> {
                findNavController().navigate(R.id.action_to_blog)
            }
            NotificationTypes.NEW_PRODUCTS -> {
                findNavController().navigate(R.id.action_to_product)
            }
            NotificationTypes.OFFERS -> {
                findNavController().navigate(R.id.action_to_offers_category)
            }
            NotificationTypes.DEFAULT -> {
                findNavController().navigate(R.id.action_to_home)
            }
        }
    }
}