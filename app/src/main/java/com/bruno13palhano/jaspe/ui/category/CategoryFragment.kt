package com.bruno13palhano.jaspe.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.common.getCategoryList
import com.google.android.material.appbar.MaterialToolbar

class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.category_list)

        val adapter = CategoryListAdapter {
            CategorySimpleStateHolder.navigateToCommonCategories(findNavController(), it)
        }
        recyclerView.adapter = adapter
        adapter.submitList(getCategoryList())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_category)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.categories_label)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}