package com.bruno13palhano.jaspe.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.ProductCardItemBinding
import com.bruno13palhano.model.Product

class ProductItemAdapter(
    private val onClick: (product: Product) -> Unit
) : ListAdapter<Product, ProductItemAdapter.AmazonItemViewHolder>(AmazonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmazonItemViewHolder {
        val binding = ProductCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AmazonItemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AmazonItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class AmazonItemViewHolder(
        val binding: ProductCardItemBinding,
        val onClick: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentProduct: Product? = null

        init {
            binding.card.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Product) {
            currentProduct = item
            binding.image .load(item.productUrlImage)
            binding.name.text = item.productName
            binding.price.text = itemView.resources.getString(R.string.product_price_label, item.productPrice)
        }
    }

    private class AmazonDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}