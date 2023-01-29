package com.bruno13palhano.jaspe.ui.category

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

class CategoriesItemAdapter(
    private val onClick: (product: Product) -> Unit
) : ListAdapter<Product, CategoriesItemAdapter.CategoriesItemViewHolder>(CategoriesDiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesItemViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories_card_item, parent, false) as CardView
        return CategoriesItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(
        holder: CategoriesItemViewHolder,
        position: Int
    ) {
        holder.bind(currentList[position])
    }

    class CategoriesItemViewHolder(
        rootView: CardView,
        val onClick: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {

        private val productImage: ImageView = rootView.findViewById(R.id.product_image)
        private val productName: TextView = rootView.findViewById(R.id.product_name)
        private val productType: TextView = rootView.findViewById(R.id.product_type)
        private val productPrice: TextView = rootView.findViewById(R.id.product_price)

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
            productImage.load(item.productUrlImage)
            productName.text = item.productName
            productType.text = item.productType
            productPrice.text = itemView.resources.getString(R.string.product_price_label, item.productPrice)
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