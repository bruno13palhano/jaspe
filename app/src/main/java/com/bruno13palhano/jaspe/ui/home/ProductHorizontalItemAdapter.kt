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

class ProductHorizontalItemAdapter(
    private val onClick: (product: Product) -> Unit
) : ListAdapter<Product, ProductHorizontalItemAdapter
.ProductHorizontalItemViewHolder>(ProductHorizontalCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductHorizontalItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_card_horizontal, parent, false) as CardView
        return ProductHorizontalItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ProductHorizontalItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ProductHorizontalItemViewHolder(
        rootView: CardView,
        val onClick: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val productImage: ImageView = rootView.findViewById(R.id.product_image)
        private val productDescription: TextView = rootView.findViewById(R.id.product_description)
        private val productPrice: TextView = rootView.findViewById(R.id.product_price)

        private var currentProduct: Product? = null

        init {
            rootView.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Product) {
            currentProduct = item
            productImage.load(item.productUrlImage)
            productDescription.text = item.productDescription
            productPrice.text = itemView.resources.getString(R.string.product_price_label, item.productPrice)
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