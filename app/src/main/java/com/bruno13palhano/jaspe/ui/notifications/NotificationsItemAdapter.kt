package com.bruno13palhano.jaspe.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.NotificationCardItemBinding
import com.bruno13palhano.model.Notification
import com.bruno13palhano.model.NotificationTypes

class NotificationsItemAdapter(
    private val onCloseClick: (notification: Notification) -> Unit,
    private val onItemClick: (type: String) -> Unit
) : ListAdapter<Notification, NotificationsItemAdapter
        .NotificationItemViewHolder>(NotificationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        val binding = NotificationCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationItemViewHolder(binding, onCloseClick, onItemClick)
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class NotificationItemViewHolder(
        val binding: NotificationCardItemBinding,
        val onCloseClick: (notification: Notification) -> Unit,
        val onItemClick: (type: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentNotification: Notification? = null

        init {
            binding.notificationClose.setOnClickListener {
                currentNotification?.let {
                    onCloseClick(it)
                }
            }

            binding.root.setOnClickListener {
                currentNotification?.let {
                    onItemClick(it.type)
                }
            }
        }

        fun bind(item: Notification) {
            currentNotification = item
            binding.notificationTitle.text = item.title
            binding.notificationDescription.text = item.description
            setNotificationIcon(binding.notificationIcon, item.type)
        }

        private fun setNotificationIcon(iconView: ImageView, type: String) {
            when (type) {
                NotificationTypes.ANNOUNCEMENT.type -> {
                    iconView.setImageResource(R.drawable.ic_baseline_announcement_24)
                }
                NotificationTypes.NEW_PRODUCTS.type -> {
                    iconView.setImageResource(R.drawable.ic_baseline_new_releases_24)
                }
                NotificationTypes.OFFERS.type -> {
                    iconView.setImageResource(R.drawable.ic_baseline_loyalty_24)
                }
                NotificationTypes.DEFAULT.type -> {
                    iconView.setImageResource(R.drawable.ic_baseline_notifications_24)
                }
            }
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