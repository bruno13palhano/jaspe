package com.bruno13palhano.jaspe.ui.home

import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
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
import com.bruno13palhano.jaspe.ui.common.getCategoryList
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.CategoryRoute
import com.bruno13palhano.model.ContactInfo
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var contactInfo = ContactInfo()

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
        val highlightsRecyclerView = view.findViewById<RecyclerView>(R.id.highlights_recycler_view)

        val imageMainBanner = view.findViewById<ImageView>(R.id.main_banner)
        val imageAmazonBanner = view.findViewById<ImageView>(R.id.amazon_banner_image)
        val imageNaturaBanner = view.findViewById<ImageView>(R.id.natura_banner_image)
        val imageAvonBanner = view.findViewById<ImageView>(R.id.avon_banner_image)

        val viewMoreAmazon = view.findViewById<CardView>(R.id.amazon_more_products)
        val viewMoreNatura = view.findViewById<CardView>(R.id.natura_more_products)
        val viewMoreAvon = view.findViewById<CardView>(R.id.avon_more_products)
        val viewMoreHighlights = view.findViewById<CardView>(R.id.highlights_more_products)

        val highlightsCard = view.findViewById<CardView>(R.id.highlights_card)

        val viewModel = requireActivity().applicationContext.let {
            ViewModelFactory(it, this@HomeFragment).createHomeViewModel()
        }

        viewMoreAmazon.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToMarketCategory()
            view.findNavController().navigate(action)
        }

        viewMoreNatura.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToNaturaCategory()
            view.findNavController().navigate(action)
        }

        viewMoreAvon.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAvonCategory()
            view.findNavController().navigate(action)
        }

        viewMoreHighlights.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToHighlightsCategory()
            view.findNavController().navigate(action)
        }

        searchProduct.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToSearchDialog()
            view.findNavController().navigate(action)
        }

        val adapter = HomeItemAdapter { productUrlLink, productSeen ->
            lifecycle.coroutineScope.launch {
                viewModel.updateProductLastSeen(productUrlLink, productSeen)
            }
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }

        val amazonAdapter = ProductItemAdapter { productUrlLink, productSeen ->
            lifecycle.coroutineScope.launch {
                viewModel.updateProductLastSeen(productUrlLink, productSeen)
            }
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }

        val naturaAdapter = ProductItemAdapter { productUrlLink, productSeen ->
            lifecycle.coroutineScope.launch {
                viewModel.updateProductLastSeen(productUrlLink, productSeen)
            }
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }

        val avonAdapter = ProductItemAdapter { productUrlLink, productSeen ->
            lifecycle.coroutineScope.launch {
                viewModel.updateProductLastSeen(productUrlLink, productSeen)
            }
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }

        val highlightsAdapter = ProductHorizontalItemAdapter { productUrlLink ->
            val action = HomeFragmentDirections.actionHomeToProduct(productUrlLink)
            view.findNavController().navigate(action)
        }
        highlightsRecyclerView.adapter = highlightsAdapter

        val categoryItems = getCategoryList()
        val categoryRecyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        val categoryAdapter = CategoryItemAdapter {
            val action = categoryNavigateTo(it)
            if (action != null) {
                view.findNavController().navigate(action)
            }
        }
        categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.submitList(categoryItems)

        lifecycle.coroutineScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainBanner.collect {
                    imageMainBanner.load(it.bannerUrlImage)
                }
            }
        }

        lifecycle.coroutineScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allProducts.collect {
                    adapter.submitList(it)
                }
            }
        }
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.amazonProducts.collect {
                    amazonAdapter.submitList(it)
                }
            }
        }
        amazonRecycler.adapter = amazonAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.naturaProducts.collect {
                    naturaAdapter.submitList(it)
                }
            }
        }
        naturaRecycler.adapter = naturaAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.avonProducts.collect {
                    avonAdapter.submitList(it)
                }
            }
        }
        avonRecycler.adapter = avonAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.amazonBanner.collect {
                    imageAmazonBanner.load(it.bannerUrlImage)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.naturaBanner.collect {
                    imageNaturaBanner.load(it.bannerUrlImage)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.avonBanner.collect {
                    imageAvonBanner.load(it.bannerUrlImage)
                }
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.contactInfo.collect {
                contactInfo = it
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.productLastSeen.collect {
                highlightsAdapter.submitList(it)
                if (it.size >= 6) {
                    highlightsCard.visibility = VISIBLE
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

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.whatsappChat -> {
                    openWhatsApp(this.requireContext(), contactInfo.contactWhatsApp)
                    true
                }
                else -> false
            }
        }
    }

    private fun categoryNavigateTo(categoryRoute: String): NavDirections? {
        when (categoryRoute) {
            CategoryRoute.BABY.route -> {
                return HomeFragmentDirections.actionHomeToBabyCategory()
            }
            CategoryRoute.MARKET.route -> {
                return HomeFragmentDirections.actionHomeToMarketCategory()
            }
            CategoryRoute.AVON.route -> {
                return HomeFragmentDirections.actionHomeToAvonCategory()
            }
            CategoryRoute.BLOG.route -> {
                return HomeFragmentDirections.actionHomeToBlogCategory()
            }
            CategoryRoute.HIGHLIGHTS.route -> {
                return HomeFragmentDirections.actionHomeToHighlightsCategory()
            }
            CategoryRoute.NATURA.route -> {
                return HomeFragmentDirections.actionHomeToNaturaCategory()
            }
            CategoryRoute.OFFERS.route -> {
                return HomeFragmentDirections.actionHomeToOffersCategory()
            }
        }

        return null
    }
}