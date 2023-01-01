package com.bruno13palhano.jaspe.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory

class FavoritesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.favorite_list)

        val adapter = FavoritesItemAdapter{
            val action = FavoritesFragmentDirections.actionFavoriteToProduct(it)
            view.findNavController().navigate(action)
        }

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@FavoritesFragment).createFavoritesViewModel()
        }

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel?.getAllFavorites()?.collect {
                adapter.submitList(it)
            }
        }

        recyclerView.adapter = adapter

        return view
    }

}