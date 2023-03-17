package com.bruno13palhano.jaspe.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.SearchDialogBinding
import com.bruno13palhano.jaspe.ui.common.navigateToProduct
import com.bruno13palhano.model.Route
import com.bruno13palhano.model.SearchCache
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchDialogFragment : DialogFragment() {
    private val viewModel: SearchDialogViewModel by viewModels()
    private lateinit var inputMethodManager: InputMethodManager
    private var _binding: SearchDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val adapter = SearchCacheAdapterList(
            onItemClick = {
                prepareNavigation(view, it)
            },
            onCloseClick = {
                viewModel.deleteSearchCacheById(it)
            }
        )
        binding.searchList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchCache.collect {
                    adapter.submitList(it)
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarSearch.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        binding.searchText.requestFocus()
        inputMethodManager.showSoftInput(binding.searchText, InputMethodManager.SHOW_IMPLICIT)

        binding.searchText.setOnEditorActionListener { textView, i, _ ->
            val textValue = textView.text.toString().trim()

            if (i == EditorInfo.IME_ACTION_SEARCH && isNotTextEmpty(textValue)) {
                viewModel.insertSearchCache(
                    SearchCache(
                        searchCacheId = 0L,
                        searchCacheName = textValue
                    ))
                prepareNavigation(view, textValue)
            }

            false
        }

        binding.toolbarSearch.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isNotTextEmpty(text: String): Boolean {
        return text != ""
    }

    private fun prepareNavigation(view: View, text: String) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        navigateToProduct(
            navController = findNavController(),
            route = Route.SEARCH_DIALOG.route,
            firstArg = text,
            secondArg = ""
        )
    }
}