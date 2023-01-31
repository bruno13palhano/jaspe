package com.bruno13palhano.jaspe.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.model.SearchCache
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch


class SearchDialogFragment() : DialogFragment() {
    private lateinit var viewModel: SearchDialogViewModel
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.search_dialog, container, false)

        viewModel = requireActivity().applicationContext.let {
            ViewModelFactory(it, this@SearchDialogFragment).createSearchDialogViewModel()
        }

        inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val recyclerView = view.findViewById<RecyclerView>(R.id.search_list)
        val adapter = SearchCacheAdapterList(
            onItemClick = {
                prepareNavigation(view, it)
            },
            onCloseClick = {
                deleteSearchCache(it)
            }
        )
        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.searchCache.collect {
                adapter.submitList(it)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_search)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        val searchText: AppCompatEditText = view.findViewById(R.id.search_text)
        searchText.requestFocus()
        inputMethodManager.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT)

        searchText.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                val textValue = textView.text.toString().trim()
                insertSearchCache(textValue)
                prepareNavigation(view, textValue)
            }

            false
        }

        toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    private fun prepareNavigation(view: View, text: String) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        navigateToSearch(text)
    }

    private fun insertSearchCache(textValue: String) {
        lifecycle.coroutineScope.launch {
            viewModel.insertSearchCache(
                SearchCache(
                    searchCacheId = 0L,
                    searchCacheName = textValue
                ))
        }
    }

    private fun deleteSearchCache(id: Long) {
        lifecycle.coroutineScope.launch {
            viewModel.deleteSearchCacheById(id)
        }
    }

    private fun navigateToSearch(text: String) {
        val action = SearchDialogFragmentDirections.actionSearchDialogToSearch(text)
        findNavController().navigate(action)
    }
}