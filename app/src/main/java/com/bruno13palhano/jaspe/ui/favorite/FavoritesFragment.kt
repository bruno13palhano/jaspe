package com.bruno13palhano.jaspe.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.jaspe.ui.common.navigateToProduct
import com.bruno13palhano.model.Route
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
            onItemClose = { productUrlLink ->
                deleteProduct(productUrlLink)
            },
            onItemShare = { productName, productLink ->
                shareProduct(productName, productLink)
            },
            onItemClick = { productUrlLink ->
                navigateToProduct(
                    navController = findNavController(),
                    route = Route.FAVORITE.route,
                    value = productUrlLink
                )
            }
        )

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.allFavoritesVisible.collect {
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
                    deleteAllFavorites()
                    true
                }

                else -> false
            }
        }
    }

    private fun deleteProduct(productUrlLink: String) {
        lifecycle.coroutineScope.launch {
            viewModel.deleteProductByUrlLink(productUrlLink)
        }
    }

    private fun shareProduct(
        productName: String,
        productUrlLink: String
    ) {
        viewModel.shareProduct(
            this@FavoritesFragment.requireContext(), productName, productUrlLink)
    }

    private fun deleteAllFavorites() {
        lifecycle.coroutineScope.launch {
            viewModel.deleteAllProduct()
        }
    }
}