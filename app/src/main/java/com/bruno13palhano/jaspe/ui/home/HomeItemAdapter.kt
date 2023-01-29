package com.bruno13palhano.jaspe.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.Product

class HomeItemAdapter(
    private val onClick: (product: Product) -> Unit
) : ListAdapter<Product, HomeItemAdapter.HomeItemViewHolder>(HomeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false) as CardView
        return HomeItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class HomeItemViewHolder(
        rootView: CardView,
        val onClick: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val productName: TextView = rootView.findViewById(R.id.product_name)
        private val productPrice: TextView = rootView.findViewById(R.id.product_price)
        private val productImage: ImageView = rootView.findViewById(R.id.product_image)

        var currentProduct: Product? = null

        init {
            rootView.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Product) {
            currentProduct = item
            productName.text = item.productName
            productPrice.text = itemView.resources.getString(R.string.product_price_label, item.productPrice)
            productImage.load(item.productUrlImage)
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