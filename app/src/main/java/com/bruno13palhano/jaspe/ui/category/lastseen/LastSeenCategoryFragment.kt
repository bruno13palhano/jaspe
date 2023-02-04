package com.bruno13palhano.jaspe.ui.category.lastseen

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
import com.bruno13palhano.model.Route
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch

class LastSeenCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_last_seen_category, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.highlights_category_list)

        val viewModel = requireActivity().applicationContext.let {
            CategoriesViewModelFactory(it, this@LastSeenCategoryFragment)
                .createLastSeenCategoryViewModel()
        }

        val adapter = CategoriesItemAdapter { product ->
            navigateToProduct(
                navController = findNavController(),
                route = Route.LAST_SEEN.route,
                firstArg = product.productUrlLink,
                secondArg = product.productType
            )
        }
        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            viewModel.productLastSeen.collect {
                adapter.submitList(it)
            }
        }

        val filterButton = view.findViewById<MaterialTextView>(R.id.filter_options_button)
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
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_highlights_category)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setTitle(R.string.last_seen_category_label)

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}