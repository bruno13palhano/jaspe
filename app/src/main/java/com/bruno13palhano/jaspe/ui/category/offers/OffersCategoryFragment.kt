package com.bruno13palhano.jaspe.ui.category.offers

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

class OffersCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_offers_category, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.offers_category_list)

        val adapter = CategoriesItemAdapter {
            val action = OffersCategoryFragmentDirections
                .actionOffersCategoryToProduct(it)
            view.findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        val viewModel = activity?.applicationContext?.let {
            CategoriesViewModelFactory(it, this@OffersCategoryFragment).createOffersCategoryViewModel()
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel?.getAllProducts()?.collect {
                adapter.submitList(it)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_offers_category)
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setTitle(R.string.offers_category_label)

        toolbar.setOnMenuItemClickListener {
            println("Menu in offers")
            false
        }

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}