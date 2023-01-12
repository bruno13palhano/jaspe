package com.bruno13palhano.jaspe.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.CategoryItem

class CategoryListAdapter(
    private val onCLick: (route: String) -> Unit
) : ListAdapter<CategoryItem, CategoryListAdapter.CategoryListViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_card_list, parent, false) as CardView
        return CategoryListViewHolder(view, onCLick)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class CategoryListViewHolder(
        rootView: CardView,
        val onCLick: (route: String) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val routeTitle: TextView = rootView.findViewById(R.id.category_title)
        var currentCategoryItem: CategoryItem? = null

        init {
            rootView.setOnClickListener {
                currentCategoryItem?.let {
                    onCLick(it.categoryItemRoute)
                }
            }
        }

        fun bind(item: CategoryItem) {
            currentCategoryItem = item
            routeTitle.text = item.categoryItemTitle
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