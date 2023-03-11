package com.bruno13palhano.jaspe.ui.home

import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.common.getCategoryList
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.Route
import com.bruno13palhano.model.ContactInfo
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var contactInfo = ContactInfo()
    private val viewModel: HomeViewModel by viewModels()
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

        val categoryItems = getCategoryList()
        val categoryRecyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        val categoryAdapter = CategoryItemAdapter {
            HomeSimpleStateHolder.navigateTo(findNavController(), it)
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

        val drawer = ((activity as MainActivity)).findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = ((activity as MainActivity)).findViewById<NavigationView>(R.id.nav_view)

        val notificationItemMenu = navView.menu.findItem(R.id.notificationsFragment)
        val notificationCountView = notificationItemMenu
            .actionView?.findViewById<TextView>(R.id.notification_count)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notificationCount.collect { nonVisualizedNotificationsCount ->
                setNotificationsCountView(notificationCountView, nonVisualizedNotificationsCount)
            }
        }

        val profilePhotoView = navView.getHeaderView(0)
            .findViewById<ShapeableImageView>(R.id.profile_photo)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileUrlPhoto.collect {
                if (it.isNotEmpty()) {
                    profilePhotoView.load(it)
                } else {
                    profilePhotoView.setImageResource(R.drawable.ic_baseline_account_circle_24)
                }
            }
        }

        val profileHeader = navView.getHeaderView(0)
            .findViewById<CardView>(R.id.profile_header)
        profileHeader.setOnClickListener {
            findNavController().currentDestination?.let {
                if (it.id == R.id.accountFragment) {
                    drawer.close()
                } else {
                    drawer.close()
                    findNavController().apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_account)
                    }
                }
            }
        }

        val usernameView = navView.getHeaderView(0)
            .findViewById<TextView>(R.id.username)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.username.collect {
                if (it.isNotEmpty()) {
                    usernameView.text = getString(R.string.welcome_user_label, it)
                } else {
                    usernameView.text = getString(R.string.welcome_user_default_label)
                }
            }
        }

        toolbar.setNavigationOnClickListener {
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
            HomeSimpleStateHolder.
            navigateTo(findNavController(), Route.MARKET.route)
        }

        viewMoreNatura.setOnClickListener {
            HomeSimpleStateHolder
                .navigateTo(findNavController(), Route.NATURA.route)
        }

        viewMoreAvon.setOnClickListener {
            HomeSimpleStateHolder
                .navigateTo(findNavController(), Route.AVON.route)
        }

        viewMoreLastSeen.setOnClickListener {
            HomeSimpleStateHolder
                .navigateTo(findNavController(), Route.LAST_SEEN.route)
        }

        searchProduct.setOnClickListener {
            HomeSimpleStateHolder
                .navigateTo(findNavController(), Route.SEARCH_DIALOG.route)
        }

        val adapter = HomeItemAdapter { product ->
            viewModel.onProductItemClick(findNavController(), product)
        }
        recyclerView.adapter = adapter

        val amazonAdapter = ProductItemAdapter { product ->
            viewModel.onProductItemClick(findNavController(), product)
        }
        amazonRecycler.adapter = amazonAdapter

        val naturaAdapter = ProductItemAdapter { product ->
            viewModel.onProductItemClick(findNavController(), product)
        }
        naturaRecycler.adapter = naturaAdapter

        val avonAdapter = ProductItemAdapter { product ->
            viewModel.onProductItemClick(findNavController(), product)
        }
        avonRecycler.adapter = avonAdapter

        val lastSeenAdapter = ProductHorizontalItemAdapter { product ->
            viewModel.onProductItemClick(findNavController(), product)
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

    private fun setNotificationsCountView(countView: TextView?, count: Long) {
        countView?.let {
            if (count > 0) {
                it.visibility = VISIBLE
                it.text = count.toString()
            } else {
                it.visibility = GONE
            }
        }
    }

    private fun setLastSeenCardVisibility(listSize: Int){
        if (listSize >= 6) {
            lastSeenCard.visibility = VISIBLE
        }
    }
}