package com.bruno13palhano.jaspe.ui.category.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.category.CategoriesItemAdapter
import com.bruno13palhano.jaspe.ui.search.FilterSearchDialogFragment
import com.bruno13palhano.jaspe.ui.search.FilterType
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Route
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private val viewModel: CategoriesViewModel by viewModels()
    private var categoryRoute = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.categories_common_fragment, container, false)
        val commonRecyclerView = view.findViewById<RecyclerView>(R.id.common_category_list)
        val quantityProducts = view.findViewById<MaterialTextView>(R.id.products_quantity)

        categoryRoute = CategoriesFragmentArgs.fromBundle(requireArguments())
            .categoryRoute

        viewModel.setProducts(categoryRoute)

        val adapter = CategoriesItemAdapter { product ->
            onProductItemClick(product)
        }
        commonRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allProducts.collect {
                    adapter.submitList(it)
                    quantityProducts.text = getString(R.string.quantity_of_products_label, it.size)
                }
            }
        }

        val filterButton = view.findViewById<MaterialTextView>(R.id.filter_options_button)
        filterButton.setOnClickListener {
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
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_common_category)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = setToolbarTitle(categoryRoute)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onProductItemClick(product: Product) {
        insertLastSeenProduct(product)
        findNavController().navigate(CategoriesFragmentDirections
            .actionCategoriesToProduct(product.productUrlLink, product.productType))
    }

    private fun insertLastSeenProduct(product: Product) {
        lifecycle.coroutineScope.launch {
            viewModel.insertLastSeenProduct(product)
        }
    }

    private fun setToolbarTitle(route: String): String {
        return when (route) {
            Route.BABY.route -> {
                getString(R.string.baby_category_label)
            }
            Route.MARKET.route -> {
                getString(R.string.amazon_category_label)
            }
            Route.AVON.route -> {
                getString(R.string.avon_category_label)
            }
            Route.NATURA.route -> {
                getString(R.string.natura_category_label)
            }
            Route.OFFERS.route -> {
                getString(R.string.offers_category_label)
            }
            Route.LAST_SEEN.route -> {
                getString(R.string.last_seen_category_label)
            }
            else -> ""
        }
    }
}