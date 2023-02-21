package com.bruno13palhano.jaspe.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        val notificationRecyclerView = view.findViewById<RecyclerView>(R.id.notifications_list)

        val viewModel = ViewModelFactory(requireContext(),
            this@NotificationsFragment).createNotificationViewModel()

        val adapter = NotificationsItemAdapter {
           lifecycleScope.launch {
               viewModel.deleteNotification(it)
           }
        }
        notificationRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.notifications.collect {
                adapter.submitList(it)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_notifications)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.notifications_label)

        toolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}