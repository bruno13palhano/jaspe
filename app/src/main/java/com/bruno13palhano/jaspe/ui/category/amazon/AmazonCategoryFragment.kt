package com.bruno13palhano.jaspe.ui.category.amazon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.category.CategoriesItemAdapter
import com.bruno13palhano.jaspe.ui.category.CategoriesViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class AmazonCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_amazon_category, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.amazon_category_list)

        val viewModel = requireActivity().applicationContext.let {
            CategoriesViewModelFactory(it, this@AmazonCategoryFragment).createAmazonCategoryViewModel()
        }

        val adapter = CategoriesItemAdapter { product ->
            lifecycle.coroutineScope.launch {
                viewModel.insertLastSeenProduct(product)
            }
            val action = AmazonCategoryFragmentDirections
                .actionMarketCategoryToProduct(product.productUrlLink)
            view.findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.getAllProducts().collect {
                adapter.submitList(it)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_amazon_category)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setTitle(R.string.amazon_category_label)

        toolbar.setOnMenuItemClickListener {
            println("Menu in amazon")
            false
        }

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}