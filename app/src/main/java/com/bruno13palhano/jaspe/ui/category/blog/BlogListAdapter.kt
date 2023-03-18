package com.bruno13palhano.jaspe.ui.category.blog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.databinding.BlogCardItemBinding
import com.bruno13palhano.model.BlogPost

class BlogListAdapter(
    private val onItemClick: (postUrl: String) -> Unit
) : ListAdapter<BlogPost, BlogListAdapter.BlogItemViewHolder>(BlogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogItemViewHolder {
        val binding = BlogCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BlogItemViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: BlogItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class BlogItemViewHolder(
        val binding: BlogCardItemBinding,
        val onItemClick: (postUrl: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentPost: BlogPost? = null

        init {
            binding.root.setOnClickListener {
                currentPost?.let {
                    onItemClick(it.postUrlLink)
                }
            }
        }

        fun bind(item: BlogPost) {
            currentPost = item
            binding.postTitle.text = item.postTitle
            binding.postDescription.text = item.postDescription
            binding.postImage.load(item.postUrlImage)
        }
    }

    private class BlogDiffCallback : DiffUtil.ItemCallback<BlogPost>() {
        override fun areItemsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem == newItem
        }
    }
}