package com.bruno13palhano.jaspe.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.Product

class SearchAdapterList(
    private val onClick: (Long) -> Unit
) : ListAdapter<Product, SearchAdapterList.SearchItemViewHolder>(SearchDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_card_list, parent, false) as CardView
        return SearchItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class SearchItemViewHolder(
        rootView: CardView,
        val onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val productName: TextView = rootView.findViewById(R.id.category_title)

        var currentProduct: Product? = null

        init {
            rootView.setOnClickListener {
                currentProduct?.let {
                    onClick(it.productId)
                }
            }
        }

        fun bind(item: Product) {
            currentProduct = item
            productName.text = item.productName
        }
    }

    private class SearchDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
}