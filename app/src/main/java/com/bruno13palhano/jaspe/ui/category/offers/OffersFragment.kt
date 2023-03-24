package com.bruno13palhano.jaspe.ui.category.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OffersFragment : Fragment() {
    private val viewModel: OffersViewModel by viewModels()
    private var _binding: CategoriesCommonFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoriesCommonFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

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
        binding.toolbarCommonCategory.title = getString(R.string.offers_category_label)

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
        findNavController().navigate(OffersFragmentDirections
            .actionToProduct(productUrlLink, productType))
    }
}