package com.bruno13palhano.jaspe.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.Notification

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        val notificationRecyclerView = view.findViewById<RecyclerView>(R.id.notifications_list)

        val notificationList = listOf(
            Notification(
                title = "ofertas 1",
                description = "descrição 1"
            ),
            Notification(
                title = "ofertas 2",
                description = "descrição 2"
            ),
            Notification(
                title = "ofertas 3",
                description = "descrição 3"
            ),
        )

        val adapter = NotificationsItemAdapter {
            println(it)
        }
        notificationRecyclerView.adapter = adapter
        adapter.submitList(notificationList)

        return view
    }
}