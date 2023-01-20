package com.bruno13palhano.jaspe.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.favorite_list)

        viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@FavoritesFragment).createFavoritesViewModel()
        }!!

        val adapter = FavoritesItemAdapter(
            onItemClose = {
                lifecycle.coroutineScope.launch {
                    viewModel.deleteProduct(it)
                }
            },
            onItemShare = { productName, productLink ->
                val shareProductLink = Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/*"
                    putExtra(Intent.EXTRA_TEXT, productLink)
                    putExtra(Intent.EXTRA_TITLE, productName)
                }, null)

                startActivity(shareProductLink)
            },
            onItemClick = { productId, productUrlLink ->
                val action =
                    FavoritesFragmentDirections.actionFavoriteToProduct(productId, productUrlLink)
                view.findNavController().navigate(action)
            }
        )

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.getAllFavorites().collect {
                adapter.submitList(it)
            }
        }

        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_favorites)
        toolbar.inflateMenu(R.menu.menu_toolbar_favorites)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.favorite_label)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteAll -> {
                    lifecycle.coroutineScope.launch {
                        viewModel.getAllFavorites().collect { favoriteProductList ->
                            viewModel.deleteAllProduct(favoriteProductList)
                        }
                    }
                    true
                }

                else -> false
            }
        }
    }
}