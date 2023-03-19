package com.bruno13palhano.jaspe.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentFavoritesBinding
import com.bruno13palhano.jaspe.ui.common.categoryList
import com.bruno13palhano.model.Route

class CategoryFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = CategoryListAdapter {
            navigateToCommonCategories(Route.valueOf(it))
        }
        binding.favoriteList.adapter = adapter
        adapter.submitList(categoryList)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarFavorites.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarFavorites.title = getString(R.string.categories_label)

        binding.toolbarFavorites.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToCommonCategories(route: Route) {
        when (route) {
            Route.OFFERS -> {
                findNavController().navigate(CategoryFragmentDirections
                    .actionCategoryToOffers())
            }
            else -> {
                findNavController().navigate(CategoryFragmentDirections
                    .actionCategoryToCommonCategories(route.route))
            }
        }
    }
}