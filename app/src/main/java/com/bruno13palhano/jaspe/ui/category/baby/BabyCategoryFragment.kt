package com.bruno13palhano.jaspe.ui.category.baby

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
import com.bruno13palhano.jaspe.ui.category.CategoriesItemAdapter
import com.bruno13palhano.jaspe.ui.category.CategoriesViewModelFactory
import com.bruno13palhano.jaspe.ui.common.navigateToProduct
import com.bruno13palhano.jaspe.ui.search.FilterSearchDialogFragment
import com.bruno13palhano.jaspe.ui.search.FilterType
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Route
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class BabyCategoryFragment : Fragment() {
    private lateinit var viewModel: BabyCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_baby_category, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.baby_category_list)

        viewModel = requireActivity().applicationContext.let {
            CategoriesViewModelFactory(it, this@BabyCategoryFragment).createBabyCategoryViewModel()
        }

        val adapter = CategoriesItemAdapter { product ->
            onClickItemCategory(product)
        }
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.allProducts.collect {
                adapter.submitList(it)
            }
        }

        val filterButton = view.findViewById<MaterialButton>(R.id.filter_options_button)
        filterButton.setOnClickListener {
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
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_baby_category)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setTitle(R.string.baby_category_label)

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun onClickItemCategory(product: Product) {
        insertLastSeenProduct(product)
        navigateToProduct(
            navController = findNavController(),
            route = Route.BABY.route,
            firstArg = product.productUrlLink,
            secondArg = product.productType
        )
    }

    private fun insertLastSeenProduct(product: Product) {
        lifecycle.coroutineScope.launch {
            viewModel.insertLastSeenProduct(product)
        }
    }
}