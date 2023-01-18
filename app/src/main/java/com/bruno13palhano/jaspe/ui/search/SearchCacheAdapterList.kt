package com.bruno13palhano.jaspe.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.SearchCache

class SearchCacheAdapterList(
    private val onItemClick: (searchCacheName: String) -> Unit,
    private val onCloseClick: (searchCacheId: Long) -> Unit
) : ListAdapter<SearchCache, SearchCacheAdapterList.SearchCacheItemViewHolder>(SearchCacheDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCacheItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_cache_card_list, parent, false) as CardView
        return SearchCacheItemViewHolder(view, onItemClick, onCloseClick)
    }

    override fun onBindViewHolder(holder: SearchCacheItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class SearchCacheItemViewHolder(
        rootView: CardView,
        val onItemClick: (searchCacheName: String) -> Unit,
        val onCloseClick: (searchCacheId: Long) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val searchCacheName: TextView = rootView.findViewById(R.id.search_cache_name)
        private val removeSearchCache: CardView = rootView.findViewById(R.id.remove_search_cache)

        var currentSearchCache: SearchCache? = null

        init {
            rootView.setOnClickListener {
                currentSearchCache?.let {
                    onItemClick(it.searchCacheName)
                }
            }

            removeSearchCache.setOnClickListener {
                currentSearchCache?.let {
                    onCloseClick(it.searchCacheId)
                }
            }
        }

        fun bind(item: SearchCache) {
            currentSearchCache = item
            searchCacheName.text = item.searchCacheName
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