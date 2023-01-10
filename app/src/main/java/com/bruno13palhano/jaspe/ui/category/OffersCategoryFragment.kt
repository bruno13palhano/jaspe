package com.bruno13palhano.jaspe.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bruno13palhano.jaspe.R
import com.google.android.material.appbar.MaterialToolbar

class OffersCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_offers_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_offers_category)
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        toolbar.setOnMenuItemClickListener {
            println("Menu in offers")
            false
        }

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}