package com.bruno13palhano.jaspe.ui.home

import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.model.CategoryItem
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.home_list)
        val searchProduct = view.findViewById<CardView>(R.id.search_product)
        val amazonRecycler = view.findViewById<RecyclerView>(R.id.amazon_recycler_view)
        val naturaRecycler = view.findViewById<RecyclerView>(R.id.natura_recycler_view)
        val avonRecycler = view.findViewById<RecyclerView>(R.id.avon_recycler_view)

        val imageMainBanner = view.findViewById<ImageView>(R.id.main_banner)
        val imageAmazonBanner = view.findViewById<ImageView>(R.id.amazon_banner_image)
        val imageNaturaBanner = view.findViewById<ImageView>(R.id.natura_banner_image)
        val imageAvonBanner = view.findViewById<ImageView>(R.id.avon_banner_image)

        searchProduct.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToSearchDialog()
            view.findNavController().navigate(action)
        }

        val adapter = HomeItemAdapter { productUrlLink ->
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }

        val amazonAdapter = ProductItemAdapter { productUrlLink ->
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }

        val naturaAdapter = ProductItemAdapter { productUrlLink ->
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }

        val avonAdapter = ProductItemAdapter { productUrlLink ->
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }

        val categoryItems = createCategoryItemList()
        val categoryRecyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        val categoryAdapter = CategoryItemAdapter {
            val action = categoryNavigateTo(it)
            if (action != null) {
                view.findNavController().navigate(action)
            }
        }
        categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.submitList(categoryItems)

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@HomeFragment).createHomeViewModel()
        }

        lifecycle.coroutineScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.mainBanner?.collect {
                    imageMainBanner.load(it.bannerUrlImage)
                }
            }
        }

        lifecycle.coroutineScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.allProducts?.collect {
                    adapter.submitList(it)
                }
            }
        }
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.amazonProducts?.collect {
                    amazonAdapter.submitList(it)
                }
            }
        }
        amazonRecycler.adapter = amazonAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.naturaProducts?.collect {
                    naturaAdapter.submitList(it)
                }
            }
        }
        naturaRecycler.adapter = naturaAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.avonProducts?.collect {
                    avonAdapter.submitList(it)
                }
            }
        }
        avonRecycler.adapter = avonAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.amazonBanner?.collect {
                    imageAmazonBanner.load(it.bannerUrlImage)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.naturaBanner?.collect {
                    imageNaturaBanner.load(it.bannerUrlImage)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.avonBanner?.collect {
                    imageAvonBanner.load(it.bannerUrlImage)
                }
            }
        }

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

    private fun categoryNavigateTo(categoryRoute: String): NavDirections? {
        when (categoryRoute) {
            "category_baby" -> {
                return HomeFragmentDirections.actionHomeToBabyCategory()
            }
            "category_market" -> {
                return HomeFragmentDirections.actionHomeToMarketCategory()
            }
            "category_avon" -> {
                return HomeFragmentDirections.actionHomeToAvonCategory()
            }
            "category_blog" -> {
                return HomeFragmentDirections.actionHomeToBlogCategory()
            }
            "category_highlights" -> {
                return HomeFragmentDirections.actionHomeToHighlightsCategory()
            }
            "category_natura" -> {
                return HomeFragmentDirections.actionHomeToNaturaCategory()
            }
            "category_offers" -> {
                return HomeFragmentDirections.actionHomeToOffersCategory()
            }
        }

        return null
    }

    private fun createCategoryItemList(): List<CategoryItem> {
        return listOf<CategoryItem>(
            CategoryItem(
                "Baby",
                "category_baby",
                "ITEM_1",
                R.drawable.baby_icon
            ),

            CategoryItem(
                "Market",
                "category_market",
                "ITEM_2",
                R.drawable.market_icon
            ),

            CategoryItem(
                "Avon",
                "category_avon",
                "ITEM_3",
                R.drawable.avon_icon
            ),

            CategoryItem(
                "Natura",
                "category_natura",
                "ITEM_4",
                R.drawable.natura_icon
            ),

            CategoryItem(
                "Offers",
                "category_offers",
                "ITEM_5",
                R.drawable.offers_icon
            ),

            CategoryItem(
                "Highlights",
                "category_highlights",
                "ITEM_6",
                R.drawable.highlights_icon
            ),

            CategoryItem(
                "Blog",
                "category_blog",
                "ITEM_7",
                R.drawable.blog_icon
            ),
        )
    }
}