package com.bruno13palhano.jaspe.ui.category.blog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.BlogPost

class BlogListAdapter(
    private val onItemClick: (postUrl: String) -> Unit
) : ListAdapter<BlogPost, BlogListAdapter.BlogItemViewHolder>(BlogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.blog_card_item, parent, false) as CardView
        return BlogItemViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: BlogItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class BlogItemViewHolder(
        rootView: CardView,
        val onItemClick: (postUrl: String) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val postTitle: AppCompatTextView = rootView.findViewById(R.id.post_title)
        private val postDescription: AppCompatTextView = rootView.findViewById(R.id.post_description)
        private val postImage: AppCompatImageView = rootView.findViewById(R.id.post_image)

        private var currentPost: BlogPost? = null

        init {
            rootView.setOnClickListener {
                currentPost?.let {
                    onItemClick(it.postUrlLink)
                }
            }
        }

        fun bind(item: BlogPost) {
            currentPost = item
            postTitle.text = item.postTitle
            postDescription.text = item.postDescription
            postImage.load(item.postUrlImage)
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