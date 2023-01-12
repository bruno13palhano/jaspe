package com.bruno13palhano.jaspe.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.CategoryItem

class CategoryItemAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<CategoryItem, CategoryItemAdapter.CategoryItemViewHolder> (
        CategoryItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_card_item, parent, false) as LinearLayout
        return CategoryItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class CategoryItemViewHolder(
        rootView: LinearLayout,
        val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {

        private val categoryItemImage = rootView.findViewById<ImageView>(R.id.category_item_image)
        private val categoryItemTitle = rootView.findViewById<TextView>(R.id.category_item_title)

        var currentCategoryItem: CategoryItem? = null

        init {
            rootView.setOnClickListener {
                currentCategoryItem?.let {
                    onClick(it.categoryItemRoute)
                }
            }
        }

        fun bind(item: CategoryItem) {
            currentCategoryItem = item
            categoryItemImage.setImageResource(item.categoryItemImage)
            categoryItemTitle.text = item.categoryItemTitle
        }
    }

    private class CategoryItemDiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.categoryItemTag == newItem.categoryItemTag
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }

    }

}