package com.bruno13palhano.jaspe.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.CategoriesCardItemBinding
import com.bruno13palhano.model.Product

class CategoriesItemAdapter(
    private val onClick: (product: Product) -> Unit
) : ListAdapter<Product, CategoriesItemAdapter.CategoriesItemViewHolder>(CategoriesDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesItemViewHolder{
        val binding = CategoriesCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesItemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CategoriesItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class CategoriesItemViewHolder(
        val binding: CategoriesCardItemBinding,
        val onClick: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentProduct: Product? = null

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
            binding.productName.text = item.productName
            binding.productType.text = item.productType
            binding.productPrice.text = itemView.resources.getString(R.string.product_price_label, item.productPrice)
        }
    }

    private class CategoriesDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}