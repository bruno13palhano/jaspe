package com.bruno13palhano.jaspe.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.databinding.CategoryCardListBinding
import com.bruno13palhano.model.CategoryItem

class CategoryListAdapter(
    private val onCLick: (route: String) -> Unit
) : ListAdapter<CategoryItem, CategoryListAdapter.CategoryListViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding = CategoryCardListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryListViewHolder(binding, onCLick)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class CategoryListViewHolder(
        val binding: CategoryCardListBinding,
        val onCLick: (route: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        var currentCategoryItem: CategoryItem? = null

        init {
            binding.root.setOnClickListener {
                currentCategoryItem?.let {
                    onCLick(it.categoryItemRoute)
                }
            }
        }

        fun bind(item: CategoryItem) {
            currentCategoryItem = item
            binding.categoryTitle.text = item.categoryItemTitle
        }
    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.categoryItemRoute == newItem.categoryItemRoute
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }
    }
}