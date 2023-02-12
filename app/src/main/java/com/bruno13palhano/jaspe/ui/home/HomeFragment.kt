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

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchProduct: CardView
    private lateinit var amazonRecycler: RecyclerView
    private lateinit var naturaRecycler: RecyclerView
    private lateinit var avonRecycler: RecyclerView
    private lateinit var lastSeenRecyclerView: RecyclerView
    private lateinit var imageMainBanner: ImageView
    private lateinit var imageAmazonBanner: ImageView
    private lateinit var imageNaturaBanner: ImageView
    private lateinit var imageAvonBanner: ImageView
    private lateinit var viewMoreAmazon: CardView
    private lateinit var viewMoreNatura: CardView
    private lateinit var viewMoreAvon: CardView
    private lateinit var viewMoreLastSeen: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.home_list)
        searchProduct = view.findViewById(R.id.search_product)
        amazonRecycler = view.findViewById(R.id.amazon_recycler_view)
        naturaRecycler = view.findViewById(R.id.natura_recycler_view)
        avonRecycler = view.findViewById(R.id.avon_recycler_view)
        lastSeenRecyclerView = view.findViewById(R.id.last_seen_recycler_view)

        imageMainBanner = view.findViewById(R.id.main_banner)
        imageAmazonBanner = view.findViewById(R.id.amazon_banner_image)
        imageNaturaBanner = view.findViewById(R.id.natura_banner_image)
        imageAvonBanner = view.findViewById(R.id.avon_banner_image)

        viewMoreAmazon = view.findViewById(R.id.amazon_more_products)
        viewMoreNatura = view.findViewById(R.id.natura_more_products)
        viewMoreAvon = view.findViewById(R.id.avon_more_products)
        viewMoreLastSeen = view.findViewById(R.id.last_seen_more_products)

        lastSeenCard = view.findViewById(R.id.last_seen_card)

        viewModel = requireActivity().applicationContext.let {
            ViewModelFactory(it, this@HomeFragment).createHomeViewModel()
        }

        val categoryItems = getCategoryList()
        val categoryRecyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        val categoryAdapter = CategoryItemAdapter {
            navigateTo(it)
        }
        categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.submitList(categoryItems)

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
//            onProductItemClick(product)
            insertLastSeenProduct(product)
            findNavController().navigate(HomeFragmentDirections
                .actionHomeToMockProduct(product.productUrlLink, product.productType))
        }
        lastSeenRecyclerView.adapter = lastSeenAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainBanner.collect {
                    imageMainBanner.load(it.bannerUrlImage)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allProducts.collect {
                    adapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.amazonProducts.collect {
                    amazonAdapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.naturaProducts.collect {
                    naturaAdapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.avonProducts.collect {
                    avonAdapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.amazonBanner.collect {
                    imageAmazonBanner.load(it.bannerUrlImage)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.naturaBanner.collect {
                    imageNaturaBanner.load(it.bannerUrlImage)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.avonBanner.collect {
                    imageAvonBanner.load(it.bannerUrlImage)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactInfo.collect {
                    contactInfo = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lastSeenProducts.collect {
                    lastSeenAdapter.submitList(it)
                    setLastSeenCardVisibility(it.size)
                }
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
                findNavController().navigate(R.id.action_to_search_dialog)
            }
            Route.OFFERS.route -> {
                findNavController().navigate(R.id.action_to_offers_category)
            }
            else -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeToCommonCategories(route))
            }
        }
    }
}