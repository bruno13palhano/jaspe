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
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationsFragment : Fragment() {
    private val viewModel: NotificationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        val notificationRecyclerView = view.findViewById<RecyclerView>(R.id.notifications_list)

        val adapter = NotificationsItemAdapter(
            onCloseClick = {
                viewModel.deleteNotification(it)
            },
            onItemClick = {
                NotificationsSimpleStateHolder.navigateTo(findNavController(), it)
            }
        )
        notificationRecyclerView.adapter = adapter

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
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_notifications)
        toolbar.inflateMenu(R.menu.menu_toolbar_favorites)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.notifications_label)

        toolbar.setOnMenuItemClickListener {
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

        toolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}