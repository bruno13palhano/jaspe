package com.bruno13palhano.jaspe.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.RelatedProductsCardItemBinding
import com.bruno13palhano.model.Product

class RelatedProductItemAdapter (
    private val onClick: (product: Product) -> Unit
) : ListAdapter<Product, RelatedProductItemAdapter.RelatedItemViewHolder>(RelatedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedItemViewHolder {
        val binding = RelatedProductsCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RelatedItemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RelatedItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class RelatedItemViewHolder(
        val binding: RelatedProductsCardItemBinding,
        val onClick: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentProduct: Product? = null

        init {
            binding.root.setOnClickListener {
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

    private class RelatedDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}