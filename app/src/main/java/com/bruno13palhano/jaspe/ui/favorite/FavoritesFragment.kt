package com.bruno13palhano.jaspe.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentFavoritesBinding
import com.bruno13palhano.jaspe.ui.common.navigateToProduct
import com.bruno13palhano.model.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModels()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = FavoritesItemAdapter(
            onItemClose = { productUrlLink ->
                viewModel.deleteProductByUrlLink(productUrlLink)
            },
            onItemShare = { productName, productLink ->
                shareProduct(productName, productLink)
            },
            onItemClick = { productUrlLink, productType ->
                navigateToProduct(
                    navController = findNavController(),
                    route = Route.FAVORITE,
                    firstArg = productUrlLink,
                    secondArg = productType
                )
            }
        )

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allFavoritesVisible.collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.favoriteList.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarFavorites.inflateMenu(R.menu.menu_toolbar_favorites)
        binding.toolbarFavorites.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarFavorites.title = getString(R.string.favorite_label)

        binding.toolbarFavorites.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.toolbarFavorites.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteAll -> {
                    viewModel.deleteAllFavorites()
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun shareProduct(productName: String, productUrlLink: String) {
        val shareProduct = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/*"
            putExtra(Intent.EXTRA_TITLE, productName)
            putExtra(Intent.EXTRA_TEXT, productUrlLink)
        }, null)

        this@FavoritesFragment.requireActivity().startActivity(shareProduct)
    }
}