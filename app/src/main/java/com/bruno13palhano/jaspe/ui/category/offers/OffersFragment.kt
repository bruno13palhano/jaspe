package com.bruno13palhano.jaspe.ui.category.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.category.CategoriesItemAdapter
import com.bruno13palhano.jaspe.ui.search.FilterSearchDialogFragment
import com.bruno13palhano.jaspe.ui.search.FilterType
import com.bruno13palhano.model.Product
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OffersFragment : Fragment() {
    private val viewModel: OffersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.categories_common_fragment, container, false)
        val commonRecyclerView = view.findViewById<RecyclerView>(R.id.common_category_list)
        val quantityProducts = view.findViewById<MaterialTextView>(R.id.products_quantity)

        val adapter = CategoriesItemAdapter { product ->
            onProductItemClick(product)
        }
        commonRecyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            viewModel.allProducts.collect {
                adapter.submitList(it)
                quantityProducts.text = getString(R.string.quantity_of_products_label, it.size)
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
        toolbar.title = getString(R.string.offers_category_label)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onProductItemClick(product: Product) {
        insertLastSeenProduct(product)
        findNavController().navigate(OffersFragmentDirections
            .actionOffersToProduct(product.productUrlLink, product.productType))
    }

    private fun insertLastSeenProduct(product: Product) {
        lifecycle.coroutineScope.launch {
            viewModel.insertLastSeenProduct(product)
        }
    }
}