package com.bruno13palhano.jaspe.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.navigation.fragment.findNavController
import com.bruno13palhano.jaspe.R
import com.google.android.material.appbar.MaterialToolbar

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_search)
        toolbar.inflateMenu(R.menu.menu_toolbar_search)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.title = getString(R.string.search_label)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        val searchText: AppCompatEditText = view.findViewById(R.id.search_text)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_button -> {
                    println("Text: ${searchText.text}")
                    true
                }
                else -> false
            }
        }
    }
}