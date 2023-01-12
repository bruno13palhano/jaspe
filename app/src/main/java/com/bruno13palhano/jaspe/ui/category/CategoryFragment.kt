package com.bruno13palhano.jaspe.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.category_list)

        val adapter = CategoryListAdapter {

        }
        recyclerView.adapter = adapter

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@CategoryFragment).createCategoryViewModel()
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            adapter.submitList(viewModel?.categoryList)
        }

        return view
    }
}