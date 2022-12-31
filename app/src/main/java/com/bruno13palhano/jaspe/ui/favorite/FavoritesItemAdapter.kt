package com.bruno13palhano.jaspe.ui.favorite

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

class FavoritesItemAdapter(
    private val onClick: (Long) -> Unit
) : ListAdapter<Product, FavoritesItemAdapter.FavoritesItemViewHolder>(FavoritesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_card, parent, false) as CardView
        return FavoritesItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: FavoritesItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class FavoritesItemViewHolder(
        rootView: CardView,
        val onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val productImage: ImageView = rootView.findViewById(R.id.product_image)
        private val productName: TextView = rootView.findViewById(R.id.product_name)
        private val productType: TextView = rootView.findViewById(R.id.product_type)
        private val productPrice: TextView = rootView.findViewById(R.id.product_price)

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
            productType.text = item.productType
        }
    }

    private class FavoritesDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}