package com.bruno13palhano.jaspe.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.home_list)
        val amazonRecycler = view.findViewById<RecyclerView>(R.id.amazon_recycler_view)
        val naturaRecycler = view.findViewById<RecyclerView>(R.id.natura_recycler_view)
        val avonRecycler = view.findViewById<RecyclerView>(R.id.avon_recycler_view)

        val adapter = HomeItemAdapter {
            val action = HomeFragmentDirections.actionHomeToProduct(it)
            view.findNavController().navigate(action)
        }

        val amazonAdapter = ProductItemAdapter {
            val action = HomeFragmentDirections.actionHomeToProduct(it)
            view.findNavController().navigate(action)
        }

        val naturaAdapter = ProductItemAdapter {
            val action = HomeFragmentDirections.actionHomeToProduct(it)
            view.findNavController().navigate(action)
        }

        val avonAdapter = ProductItemAdapter {
            val action = HomeFragmentDirections.actionHomeToProduct(it)
            view.findNavController().navigate(action)
        }

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@HomeFragment).createHomeViewModel()
        }

//        lifecycle.coroutineScope.launch {
//            viewModel?.insertProduct(
//                Product(
//                    productName = "Desodorante",
//                    productUrlImage = R.drawable.perfume_6.toString(),
//                    productPrice = 24.90f,
//                    productType = "Desodorante Antitranspirante",
//                    productDescription = "Roll-On Tododia Leite de Algodão",
//                    productCompany = "Natura",
//                    productUrlLink = "https://www.natura.com.br/p/desodorante-antitranspirante-roll-on-tododia-leite-de-algodao-70-ml/69669?consultoria=default&listTitle=search%20results%20list%20showcase%20-%20desodorante%20antitranspirante&position=3",
//                    productIsFavorite = false
//                )
//            )
//        }

        lifecycle.coroutineScope.launch {
            viewModel?.getAllProducts()?.collect {
                adapter.submitList(it)
                amazonAdapter.submitList(it)
                naturaAdapter.submitList(it)
                avonAdapter.submitList(it)
            }
        }

        recyclerView.adapter = adapter
        amazonRecycler.adapter = amazonAdapter
        naturaRecycler.adapter = naturaAdapter
        avonRecycler.adapter = avonAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)

        toolbar.setNavigationOnClickListener {
            val drawer = ((activity as MainActivity)).findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.open()
        }
    }
}