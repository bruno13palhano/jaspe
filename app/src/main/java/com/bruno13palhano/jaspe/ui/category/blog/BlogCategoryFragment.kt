package com.bruno13palhano.jaspe.ui.category.blog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentBlogCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BlogCategoryFragment : Fragment() {
    private val viewModel: BlogViewModel by viewModels()
    private var _binding: FragmentBlogCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        val blogAdapter = BlogListAdapter {

        }
        binding.blogCategoryList.adapter = blogAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allBlogPosts.collect {
                    blogAdapter.submitList(it)
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarBlogCategory.inflateMenu(R.menu.menu_toolbar)
        binding.toolbarBlogCategory.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarBlogCategory.setTitle(R.string.blog_category_label)

        binding.toolbarBlogCategory.setOnMenuItemClickListener {
            false
        }

        binding.toolbarBlogCategory.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}