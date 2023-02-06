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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.jaspe.ui.common.getCategoryList
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.Route
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.model.Product
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var contactInfo = ContactInfo()
    private lateinit var viewModel: HomeViewModel
    private lateinit var lastSeenCard: CardView

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
        val lastSeenRecyclerView = view.findViewById<RecyclerView>(R.id.last_seen_recycler_view)

        val imageMainBanner = view.findViewById<ImageView>(R.id.main_banner)
        val imageAmazonBanner = view.findViewById<ImageView>(R.id.amazon_banner_image)
        val imageNaturaBanner = view.findViewById<ImageView>(R.id.natura_banner_image)
        val imageAvonBanner = view.findViewById<ImageView>(R.id.avon_banner_image)

        val viewMoreAmazon = view.findViewById<CardView>(R.id.amazon_more_products)
        val viewMoreNatura = view.findViewById<CardView>(R.id.natura_more_products)
        val viewMoreAvon = view.findViewById<CardView>(R.id.avon_more_products)
        val viewMoreLastSeen = view.findViewById<CardView>(R.id.last_seen_more_products)

        lastSeenCard = view.findViewById(R.id.last_seen_card)

        viewModel = requireActivity().applicationContext.let {
            ViewModelFactory(it, this@HomeFragment).createHomeViewModel()
        }

        viewMoreAmazon.setOnClickListener {
            navigateTo(Route.MARKET.route)
        }

        viewMoreNatura.setOnClickListener {
            navigateTo(Route.NATURA.route)
        }

        viewMoreAvon.setOnClickListener {
            navigateTo(Route.AVON.route)
        }

        viewMoreLastSeen.setOnClickListener {
            navigateTo(Route.LAST_SEEN.route)
        }

        searchProduct.setOnClickListener {
            navigateTo(Route.SEARCH_DIALOG.route)
        }

        val adapter = HomeItemAdapter { product ->
            onProductItemClick(product)
        }
        recyclerView.adapter = adapter

        val amazonAdapter = ProductItemAdapter { product ->
            onProductItemClick(product)
        }
        amazonRecycler.adapter = amazonAdapter

        val naturaAdapter = ProductItemAdapter { product ->
            onProductItemClick(product)
        }
        naturaRecycler.adapter = naturaAdapter

        val avonAdapter = ProductItemAdapter { product ->
            onProductItemClick(product)
        }
        avonRecycler.adapter = avonAdapter

        val lastSeenAdapter = ProductHorizontalItemAdapter { product ->
            onProductItemClick(product)
        }
        lastSeenRecyclerView.adapter = lastSeenAdapter

        val categoryItems = getCategoryList()
        val categoryRecyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        val categoryAdapter = CategoryItemAdapter {
            navigateTo(it)
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

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.amazonProducts.collect {
                    amazonAdapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.naturaProducts.collect {
                    naturaAdapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.avonProducts.collect {
                    avonAdapter.submitList(it)
                }
            }
        }

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
            viewModel.lastSeenProducts.collect {
                lastSeenAdapter.submitList(it)
                setLastSeenCardVisibility(it.size)
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
                    openWhatsApp(this.requireContext(), contactInfo.contactWhatsApp, "")
                    true
                }
                else -> false
            }
        }
    }

    private fun setLastSeenCardVisibility(listSize: Int){
        if (listSize >= 6) {
            lastSeenCard.visibility = VISIBLE
        }
    }

    private fun onProductItemClick(product: Product) {
        insertLastSeenProduct(product)
        navigateToProduct(product.productUrlLink, product.productType)
    }

    private fun insertLastSeenProduct(product: Product) {
        lifecycle.coroutineScope.launch {
            viewModel.insertLastSeenProduct(product)
        }
    }

    private fun navigateToProduct(productUrlLink: String, productType: String) {
        findNavController().navigate(HomeFragmentDirections
            .actionHomeToProduct(productUrlLink, productType))
    }

    private fun navigateTo(route: String) {
        when (route) {
            Route.SEARCH_DIALOG.route -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeToSearchDialog())
            }
            Route.OFFERS.route -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeToOffersCategory())
            }
            else -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeToCommonCategories(route))
            }
        }
    }
}