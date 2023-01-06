package com.bruno13palhano.jaspe.ui.home

import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
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
//                    productName = "Musk",
//                    productUrlImage = R.drawable.avon_6.toString(),
//                    productPrice = 59.90f,
//                    productType = "Perfume Masculino",
//                    productDescription = "Musk+ Marine Deo Colônia - 75ml",
//                    productCompany = "Avon",
//                    productUrlLink = "https://www.avon.com.br/1394447-deo-colonia-musk--marine---75ml/p?sci=138493979&scn=product%2520products_mais_opcoes&scpi=138493979&rec_id=63b54cd473686f0010017f04#2625610158693000340",
//                    productIsFavorite = false
//                )
//            )
//        }

        lifecycle.coroutineScope.launch {
            viewModel?.getAllProducts()?.collect {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel?.getAmazonProducts()?.collect {
                amazonAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel?.getNaturaProducts()?.collect {
                naturaAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel?.getAvonProducts()?.collect {
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