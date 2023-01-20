package com.bruno13palhano.jaspe.ui.favorite

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
import com.bruno13palhano.model.FavoriteProduct

class FavoritesItemAdapter(
    private val onItemClick: (productId: Long, productUrlLink: String) -> Unit,
    private val onItemClose: (productId: Long) -> Unit,
    private val onItemShare: (productName: String, productLink: String) -> Unit
) : ListAdapter<FavoriteProduct, FavoritesItemAdapter.FavoritesItemViewHolder>(FavoritesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_card, parent, false) as CardView
        return FavoritesItemViewHolder(view, onItemClose, onItemClick, onItemShare)
    }

    override fun onBindViewHolder(holder: FavoritesItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class FavoritesItemViewHolder(
        rootView: CardView,
        val onItemClose: (Long) -> Unit,
        val onItemClick: (productUd: Long, productUrlLink: String) -> Unit,
        val onItemShare: (productName: String, productLink: String) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val productImage: ImageView = rootView.findViewById(R.id.product_image)
        private val productName: TextView = rootView.findViewById(R.id.product_name)
        private val productType: TextView = rootView.findViewById(R.id.product_type)
        private val productPrice: TextView = rootView.findViewById(R.id.product_price)
        private val removeProduct: CardView = rootView.findViewById(R.id.remove_product)
        private val shareProduct: CardView = rootView.findViewById(R.id.share_product)

        var currentProduct: FavoriteProduct? = null

        init {
            removeProduct.setOnClickListener {
                currentProduct?.let {
                    onItemClose(it.favoriteProductId)
                }
            }

            shareProduct.setOnClickListener {
                currentProduct?.let {
                    onItemShare(it.favoriteProductName, it.favoriteProductUrlLink)
                }
            }

            rootView.setOnClickListener {
                currentProduct?.let {
                    onItemClick(it.favoriteProductId, it.favoriteProductUrlLink)
                }
            }
        }

        fun bind(item: FavoriteProduct) {
            currentProduct = item
            productImage.load(item.favoriteProductUrlImage)
            productName.text = item.favoriteProductName
            productPrice.text =itemView.resources.getString(R.string.product_price_label, item.favoriteProductPrice)
            productType.text = item.favoriteProductType
        }
    }

    private class FavoritesDiffCallback : DiffUtil.ItemCallback<FavoriteProduct>() {
        override fun areItemsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem.favoriteProductId == newItem.favoriteProductId
        }

        override fun areContentsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem == newItem
        }
    }
}