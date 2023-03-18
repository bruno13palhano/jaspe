package com.bruno13palhano.jaspe.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.ProductCardHorizontalBinding
import com.bruno13palhano.model.Product

class ProductHorizontalItemAdapter(
    private val onClick: (product: Product) -> Unit
) : ListAdapter<Product, ProductHorizontalItemAdapter
.ProductHorizontalItemViewHolder>(ProductHorizontalCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductHorizontalItemViewHolder {
        val binding = ProductCardHorizontalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHorizontalItemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ProductHorizontalItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ProductHorizontalItemViewHolder(
        val binding: ProductCardHorizontalBinding,
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
            binding.productImage.load(item.productUrlImage)
            binding.productDescription.text = item.productDescription
            binding.productPrice.text = itemView.resources.getString(R.string.product_price_label, item.productPrice)
        }
    }

    private class ProductHorizontalCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productUrlLink == newItem.productUrlLink
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}