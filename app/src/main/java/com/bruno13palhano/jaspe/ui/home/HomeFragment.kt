package com.bruno13palhano.jaspe.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.product.ProductViewModelFactory
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.ProductRepositoryFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.home_list)

        val adapter = HomeItemAdapter {
            val action = HomeFragmentDirections.actionHomeToProduct(it)
            view.findNavController().navigate(action)
        }

        val repository = activity?.let {
            ProductRepositoryFactory(it.applicationContext).createProductRepository()
        }

        val viewModelFactory = repository?.let {
            HomeViewModelFactory(it)
        }

        val viewModel = viewModelFactory?.let {
            ViewModelProvider(this@HomeFragment, it)
        }?.get(HomeViewModel::class.java)

        lifecycle.coroutineScope.launch {
            viewModel?.getAllProducts()?.collect {
                adapter.submitList(it)
            }
        }

        recyclerView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.show()
    }
}