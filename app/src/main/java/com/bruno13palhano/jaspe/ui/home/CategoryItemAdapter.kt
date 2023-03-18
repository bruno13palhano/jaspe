package com.bruno13palhano.jaspe.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.databinding.CategoryCardItemBinding
import com.bruno13palhano.model.CategoryItem

class CategoryItemAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<CategoryItem, CategoryItemAdapter.CategoryItemViewHolder> (
        CategoryItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding = CategoryCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class CategoryItemViewHolder(
        val binding: CategoryCardItemBinding,
        val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentCategoryItem: CategoryItem? = null

        init {
            binding.categoryItemButton.setOnClickListener {
                currentCategoryItem?.let {
                    onClick(it.categoryItemRoute)
                }
            }
        }

        fun bind(item: CategoryItem) {
            currentCategoryItem = item
            binding.categoryItemButton.load(item.categoryItemImage)
            binding.categoryItemTitle.text = item.categoryItemTitle
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