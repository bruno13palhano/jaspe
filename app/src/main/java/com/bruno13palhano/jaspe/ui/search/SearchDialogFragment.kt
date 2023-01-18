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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.search_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_search)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@SearchDialogFragment).createSearchDialogViewModel()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.search_list)
        val adapter = SearchCacheAdapterList(
            onItemClick = {
                val action = SearchDialogFragmentDirections.actionSearchDialogToSearch(it)
                findNavController().navigate(action)
            },
            onCloseClick = {
                lifecycle.coroutineScope.launch {
                    viewModel?.deleteSearchCacheById(it)
                }
            }
        )
        recyclerView.adapter = adapter

        toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel?.searchCache?.collect {
                adapter.submitList(it)
            }
        }

        val searchText: AppCompatEditText = view.findViewById(R.id.search_text)
        searchText.requestFocus()
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT)

        searchText.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                lifecycle.coroutineScope.launch {
                    viewModel?.insertSearchCache(
                        SearchCache(
                            searchCacheId = 0L,
                            searchCacheName = searchText.text.toString().trim()
                        ))
                }
            }

            false
        }
    }
}