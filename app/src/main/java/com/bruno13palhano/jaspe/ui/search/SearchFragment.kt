package com.bruno13palhano.jaspe.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.jaspe.ui.common.navigateToProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Route
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var searchCacheName: String = ""
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        viewModel = requireActivity().applicationContext.let {
            ViewModelFactory(it, this@SearchFragment).createSearchViewModel()
        }

        try {
            searchCacheName = SearchFragmentArgs.fromBundle(requireArguments()).searchCacheName
        } catch (ignored: IllegalArgumentException) {}

        val recyclerView = view.findViewById<RecyclerView>(R.id.search_list)
        val adapter = SearchAdapterList { product ->
            prepareNavigation(product, product.productUrlLink, product.productType)
        }
        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.searchProducts.collect {
                adapter.submitList(it)
            }
        }

        lifecycle.coroutineScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchProduct(searchCacheName)
            }
        }

        val searchProduct: CardView = view.findViewById(R.id.search_product)
        searchProduct.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchCategoryToSearchDialog())
        }

        val filterSearch: MaterialButton = view.findViewById(R.id.filter_options)
        filterSearch.setOnClickListener {
            val filterDialog = FilterSearchDialogFragment(object : FilterSearchDialogFragment.FilterDialogListener {
                override fun onDialogPositiveClick(filter: FilterType) {
                    viewModel.getOrderProducts(filter)
                }
            })
            filterDialog.show(requireActivity().supportFragmentManager, "filter")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_search)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.search_label)

        toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    private fun prepareNavigation(
        lastSeen: Product,
        productUrl: String,
        productType: String
    ) {
        insertLastSeen(lastSeen)
        navigateToProduct(
            navController = findNavController(),
            route = Route.SEARCH.route,
            firstArg = productUrl,
            secondArg = productType
        )
    }

    private fun insertLastSeen(lastSeen: Product) {
        lifecycle.coroutineScope.launch {
            viewModel.insertLastSeenProduct(lastSeen)
        }
    }
}