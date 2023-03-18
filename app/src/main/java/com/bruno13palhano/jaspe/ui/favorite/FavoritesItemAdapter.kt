package com.bruno13palhano.jaspe.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FavoriteCardBinding
import com.bruno13palhano.model.FavoriteProduct

class FavoritesItemAdapter(
    private val onItemClick: (productUrlLink: String, productType: String) -> Unit,
    private val onItemClose: (productUrlLink: String) -> Unit,
    private val onItemShare: (productName: String, productLink: String) -> Unit
) : ListAdapter<FavoriteProduct, FavoritesItemAdapter.FavoritesItemViewHolder>(FavoritesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesItemViewHolder {
        val binding = FavoriteCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesItemViewHolder(binding, onItemClose, onItemClick, onItemShare)
    }

    override fun onBindViewHolder(holder: FavoritesItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class FavoritesItemViewHolder(
        val binding: FavoriteCardBinding,
        val onItemClose: (productUrlLink: String) -> Unit,
        val onItemClick: (productUrlLink: String, productType: String) -> Unit,
        val onItemShare: (productName: String, productLink: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentProduct: FavoriteProduct? = null

        init {
            binding.removeProduct.setOnClickListener {
                currentProduct?.let {
                    onItemClose(it.favoriteProductUrlLink)
                }
            }

            binding.shareProduct.setOnClickListener {
                currentProduct?.let {
                    onItemShare(it.favoriteProductName, it.favoriteProductUrlLink)
                }
            }

            binding.root.setOnClickListener {
                currentProduct?.let {
                    onItemClick(it.favoriteProductUrlLink, it.favoriteProductType)
                }
            }
        }

        fun bind(item: FavoriteProduct) {
            currentProduct = item
            binding.productImage.load(item.favoriteProductUrlImage)
            binding.productName.text = item.favoriteProductName
            binding.productPrice.text =itemView.resources.getString(R.string.product_price_label, item.favoriteProductPrice)
            binding.productType.text = item.favoriteProductType
        }
    }

    private class FavoritesDiffCallback : DiffUtil.ItemCallback<FavoriteProduct>() {
        override fun areItemsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem.favoriteProductUrlLink == newItem.favoriteProductUrlLink
        }

        override fun areContentsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem == newItem
        }
    }
}