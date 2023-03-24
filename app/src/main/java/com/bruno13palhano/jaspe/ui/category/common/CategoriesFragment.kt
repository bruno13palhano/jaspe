package com.bruno13palhano.jaspe.ui.category.common

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
import com.bruno13palhano.jaspe.databinding.CategoriesCommonFragmentBinding
import com.bruno13palhano.jaspe.ui.category.CategoriesItemAdapter
import com.bruno13palhano.jaspe.ui.search.FilterSearchDialogFragment
import com.bruno13palhano.jaspe.ui.search.FilterType
import com.bruno13palhano.model.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private val viewModel: CategoriesViewModel by viewModels()
    private var categoryRoute = ""
    private var _binding: CategoriesCommonFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoriesCommonFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        categoryRoute = CategoriesFragmentArgs.fromBundle(requireArguments())
            .categoryRoute

        viewModel.setProducts(categoryRoute)

        val adapter = CategoriesItemAdapter { product ->
            viewModel.insertLastSeenProduct(product)
            navigateToProduct(product.productUrlLink, product.productType)
        }
        binding.commonCategoryList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allProducts.collect {
                    adapter.submitList(it)
                    binding.productsQuantity.text = getString(R.string.quantity_of_products_label, it.size)
                }
            }
        }

        binding.filterOptionsButton.setOnClickListener {
            val filterDialog = FilterSearchDialogFragment(object  : FilterSearchDialogFragment.FilterDialogListener {
                override fun onDialogPositiveClick(filter: FilterType) {
                    viewModel.getOrderedProducts(filter)
                }
            })
            filterDialog.show(requireActivity().supportFragmentManager, "filter")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarCommonCategory.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarCommonCategory.title = setToolbarTitle(Route.valueOf(categoryRoute))

        binding.toolbarCommonCategory.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToProduct(
        productUrlLink: String,
        productType: String
    ) {
        findNavController().navigate(CategoriesFragmentDirections
            .actionToProduct(productUrlLink, productType))
    }

    private fun setToolbarTitle(route: Route): String {
        return when (route) {
            Route.BABY -> {
                requireContext().getString(R.string.baby_category_label)
            }
            Route.MARKET -> {
                requireContext().getString(R.string.amazon_category_label)
            }
            Route.AVON -> {
                requireContext().getString(R.string.avon_category_label)
            }
            Route.NATURA -> {
                requireContext().getString(R.string.natura_category_label)
            }
            Route.OFFERS -> {
                requireContext().getString(R.string.offers_category_label)
            }
            Route.LAST_SEEN -> {
                requireContext().getString(R.string.last_seen_category_label)
            }
            else -> ""
        }
    }
}