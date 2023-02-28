package com.bruno13palhano.jaspe.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.Notification

class NotificationsItemAdapter(
    private val onCloseClick: (notification: Notification) -> Unit,
    private val onItemClick: (type: String) -> Unit
) : ListAdapter<Notification, NotificationsItemAdapter
        .NotificationItemViewHolder>(NotificationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_card_item, parent, false) as CardView
        return NotificationItemViewHolder(view, onCloseClick, onItemClick)
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class NotificationItemViewHolder(
        rootView: CardView,
        val onCloseClick: (notification: Notification) -> Unit,
        val onItemClick: (type: String) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val notificationTitle: TextView = rootView.findViewById(R.id.notification_title)
        private val notificationDescription: TextView =
            rootView.findViewById(R.id.notification_description)
        private val notificationIcon: ImageView = rootView.findViewById(R.id.notification_icon)
        private val notificationClose: ImageView = rootView.findViewById(R.id.notification_close)

        var currentNotification: Notification? = null

        init {
            notificationClose.setOnClickListener {
                currentNotification?.let {
                    onCloseClick(it)
                }
            }

            rootView.setOnClickListener {
                currentNotification?.let {
                    onItemClick(it.type)
                }
            }
        }

        fun bind(item: Notification) {
            currentNotification = item
            notificationTitle.text = item.title
            notificationDescription.text = item.description

        }
    }

    private class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }
}