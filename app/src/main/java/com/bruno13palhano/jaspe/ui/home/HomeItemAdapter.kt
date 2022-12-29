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

class HomeItemAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<Product, HomeItemAdapter.HomeItemViewHolder>(HomeDiffCallback()) {
//    var data = listOf<Product>()
//        set(value) {
//            field = value
////            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false) as CardView
        return HomeItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class HomeItemViewHolder(rootView: CardView, val onClick: (String) -> Unit)
                    : RecyclerView.ViewHolder(rootView) {
        val productName = rootView.findViewById<TextView>(R.id.product_name)
        val productPrice = rootView.findViewById<TextView>(R.id.product_price)
        val productType = rootView.findViewById<TextView>(R.id.product_type)
        val productImage = rootView.findViewById<ImageView>(R.id.product_image)

        var currentProduct: Product? = null

        init {
            rootView.setOnClickListener {
                currentProduct?.let {
                    onClick(it.productName)
                }
            }
        }

        fun bind(item: Product) {
            currentProduct = item
            productName.text = item.productName
            productPrice.text = item.productPrice.toString()
            productType.text = item.productType
            productImage.setImageResource(item.productUrlImage.toInt())
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