package com.bruno13palhano.jaspe.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.databinding.SearchCacheCardListBinding
import com.bruno13palhano.model.SearchCache

class SearchCacheAdapterList(
    private val onItemClick: (searchCacheName: String) -> Unit,
    private val onCloseClick: (searchCacheId: Long) -> Unit
) : ListAdapter<SearchCache, SearchCacheAdapterList.SearchCacheItemViewHolder>(SearchCacheDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCacheItemViewHolder {
        val binding = SearchCacheCardListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchCacheItemViewHolder(binding, onItemClick, onCloseClick)
    }

    override fun onBindViewHolder(holder: SearchCacheItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class SearchCacheItemViewHolder(
        val binding: SearchCacheCardListBinding,
        val onItemClick: (searchCacheName: String) -> Unit,
        val onCloseClick: (searchCacheId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentSearchCache: SearchCache? = null

        init {
            binding.root.setOnClickListener {
                currentSearchCache?.let {
                    onItemClick(it.searchCacheName)
                }
            }

            binding.removeSearchCache.setOnClickListener {
                currentSearchCache?.let {
                    onCloseClick(it.searchCacheId)
                }
            }
        }

        fun bind(item: SearchCache) {
            currentSearchCache = item
            binding.searchCacheName.text = item.searchCacheName
        }
    }

    private class SearchCacheDiffCallback : DiffUtil.ItemCallback<SearchCache>() {
        override fun areItemsTheSame(oldItem: SearchCache, newItem: SearchCache): Boolean {
            return oldItem.searchCacheId == newItem.searchCacheId
        }

        override fun areContentsTheSame(oldItem: SearchCache, newItem: SearchCache): Boolean {
            return oldItem == newItem
        }
    }
}