package com.bruno13palhano.jaspe.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.CardItemBinding
import com.bruno13palhano.model.Product

class HomeItemAdapter(
    private val onClick: (product: Product) -> Unit
) : ListAdapter<Product, HomeItemAdapter.HomeItemViewHolder>(HomeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val binding = CardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeItemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class HomeItemViewHolder(
        val binding: CardItemBinding,
        val onClick: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentProduct: Product? = null

        init {
            binding.cardItem.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Product) {
            currentProduct = item
            binding.productName.text = item.productName
            binding.productPrice.text = itemView.resources.getString(R.string.product_price_label, item.productPrice)
            binding.productImage.load(item.productUrlImage)
        }
    }

    private class HomeDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}