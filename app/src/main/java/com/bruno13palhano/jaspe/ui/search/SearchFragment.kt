package com.bruno13palhano.jaspe.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentSearchBinding
import com.bruno13palhano.jaspe.ui.common.navigateToProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var searchCacheName: String = ""
    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        try {
            searchCacheName = SearchFragmentArgs.fromBundle(requireArguments()).searchCacheName
        } catch (ignored: IllegalArgumentException) {}

        val adapter = SearchAdapterList { product ->
            prepareNavigation(product, product.productUrlLink, product.productType)
        }
        binding.searchList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchProducts.collect {
                    adapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchProduct(searchCacheName)
            }
        }

        binding.searchProduct.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchCategoryToSearchDialog())
        }

        binding.filterOptions.setOnClickListener {
            val filterDialog = FilterSearchDialogFragment(object : FilterSearchDialogFragment.FilterDialogListener {
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
        binding.toolbarSearch.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarSearch.title = getString(R.string.search_label)

        binding.toolbarSearch.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareNavigation(
        lastSeen: Product,
        productUrl: String,
        productType: String
    ) {
        viewModel.insertLastSeenProduct(lastSeen)
        navigateToProduct(
            navController = findNavController(),
            route = Route.SEARCH.route,
            firstArg = productUrl,
            secondArg = productType
        )
    }
}