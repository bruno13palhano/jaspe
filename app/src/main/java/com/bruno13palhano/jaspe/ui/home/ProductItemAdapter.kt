package com.bruno13palhano.jaspe.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.Product

class ProductItemAdapter(
    private val onClick: (Long) -> Unit
) : ListAdapter<Product, ProductItemAdapter.AmazonItemViewHolder>(AmazonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmazonItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_card_item, parent, false) as CardView
        return AmazonItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: AmazonItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class AmazonItemViewHolder(
        rootView: CardView,
        val onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val productImage: ImageView = rootView.findViewById(R.id.image)
        private val productName: TextView = rootView.findViewById(R.id.name)
        private val productPrice: TextView = rootView.findViewById(R.id.price)

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
            productImage.setImageResource(item.productUrlImage.toInt())
            productName.text = item.productName
            productPrice.text = item.productPrice.toString()
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