package com.bruno13palhano.jaspe.ui.category.blog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.category.CategoriesViewModelFactory
import com.bruno13palhano.model.BlogPost
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class BlogCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blog_category, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.blog_category_list)

        val viewModel = requireActivity().applicationContext.let {
            CategoriesViewModelFactory(it, this@BlogCategoryFragment).createBlogViewModel()
        }

        val blogAdapter = BlogListAdapter {
            println("post url: $it")
        }
        recyclerView.adapter = blogAdapter

        lifecycle.coroutineScope.launch {
            viewModel.allBlogPosts.collect {
                blogAdapter.submitList(it)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_blog_category)
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setTitle(R.string.blog_category_label)

        toolbar.setOnMenuItemClickListener {
            println("Menu in blog")
            false
        }

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}