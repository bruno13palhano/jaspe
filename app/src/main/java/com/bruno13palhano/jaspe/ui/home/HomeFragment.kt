package com.bruno13palhano.jaspe.ui.home

import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentHomeBinding
import com.bruno13palhano.jaspe.ui.common.categoryList
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.Route
import com.bruno13palhano.model.ContactInfo
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var contactInfo = ContactInfo()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var drawer: DrawerLayout
    private lateinit var navView: NavigationView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        drawer = ((activity as MainActivity)).findViewById(R.id.drawer_layout)
        navView = ((activity as MainActivity)).findViewById(R.id.nav_view)

        val notificationItemMenu = navView.menu.findItem(R.id.notificationsFragment)
        val notificationCountView = notificationItemMenu
            .actionView?.findViewById<TextView>(R.id.notification_count)

        val profilePhotoView = navView.getHeaderView(0)
            .findViewById<ShapeableImageView>(R.id.profile_photo)

        val usernameView = navView.getHeaderView(0)
            .findViewById<TextView>(R.id.username)

        val categoryAdapter = CategoryItemAdapter {
            navigateTo(Route.valueOf(it))
        }
        binding.categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.submitList(categoryList)

        val adapter = HomeItemAdapter { product ->
            viewModel.insertLastSeenProduct(product)
            navigateToProduct(product.productUrlLink, product.productType)
        }
        binding.homeList.adapter = adapter

        val amazonAdapter = ProductItemAdapter { product ->
            viewModel.insertLastSeenProduct(product)
            navigateToProduct(product.productUrlLink, product.productType)
        }
        binding.amazonRecyclerView.adapter = amazonAdapter

        val naturaAdapter = ProductItemAdapter { product ->
            viewModel.insertLastSeenProduct(product)
            navigateToProduct(product.productUrlLink, product.productType)
        }
        binding.naturaRecyclerView.adapter = naturaAdapter

        val avonAdapter = ProductItemAdapter { product ->
            viewModel.insertLastSeenProduct(product)
            navigateToProduct(product.productUrlLink, product.productType)
        }
        binding.avonRecyclerView.adapter = avonAdapter

        val lastSeenAdapter = ProductHorizontalItemAdapter { product ->
            viewModel.insertLastSeenProduct(product)
            navigateToProduct(product.productUrlLink, product.productType)
        }
        binding.lastSeenRecyclerView.adapter = lastSeenAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        contactInfo = it.contactInfo
                        setProfileUrlPhoto(profilePhotoView, it.profileUrlPhoto)
                        setUsername(usernameView, it.username)
                        setNotificationsCountView(notificationCountView, it.notificationCount)
                    }
                }
                launch {
                    viewModel.bannersUiState.collect {
                        binding.mainBanner.load(it.mainBanner.bannerUrlImage)
                        binding.amazonBannerImage.load(it.amazonBanner.bannerUrlImage)
                        binding.naturaBannerImage.load(it.naturaBanner.bannerUrlImage)
                        binding.avonBannerImage.load(it.avonBanner.bannerUrlImage)
                    }
                }
                launch {
                    viewModel.productsUiState.collect {
                        adapter.submitList(it.products)
                        amazonAdapter.submitList(it.amazonProducts)
                        naturaAdapter.submitList(it.naturaProducts)
                        avonAdapter.submitList(it.avonProducts)
                        lastSeenAdapter.submitList(it.lastSeenProducts)
                        setLastSeenCardVisibility(it.lastSeenProducts.size)
                    }
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

        binding.amazonMoreProducts.setOnClickListener {
            navigateTo(Route.MARKET)
        }

        binding.naturaMoreProducts.setOnClickListener {
            navigateTo(Route.NATURA)
        }

        binding.avonMoreProducts.setOnClickListener {
            navigateTo(Route.AVON)
        }

        binding.lastSeenMoreProducts.setOnClickListener {
            navigateTo(Route.LAST_SEEN)
        }

        binding.searchProduct.setOnClickListener {
            navigateTo(Route.SEARCH_DIALOG)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)

        binding.toolbar.setNavigationOnClickListener {
            drawer.open()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.whatsappChat -> {
                    openWhatsApp(this.requireContext(), contactInfo.contactWhatsApp, "")
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToProduct(productUrlLink: String, productType: String) {
        findNavController().navigate(HomeFragmentDirections
            .actionToProduct(productUrlLink, productType))
    }

    private fun navigateTo(route: Route) {
        when (route) {
            Route.SEARCH_DIALOG -> {
                findNavController().apply {
                    popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                    navigate(R.id.action_to_search_dialog)
                }
            }
            Route.OFFERS -> {
                findNavController().apply {
                    popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                    navigate(R.id.action_to_offers_category)
                }
            }
            else -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionToCommonCategories(route.route))
            }
        }
    }

    private fun setNotificationsCountView(countView: TextView?, count: Int) {
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
            binding.lastSeenCard.visibility = VISIBLE
        }
    }

    private fun setProfileUrlPhoto(profilePhotoView: ShapeableImageView, photoUrl: String) {
        if (photoUrl.isNotEmpty()) {
            profilePhotoView.load(photoUrl)
        } else {
            profilePhotoView.load(R.drawable.ic_baseline_account_circle_24)
        }
    }

    private fun setUsername(usernameView: TextView, username: String) {
        if (username.isNotEmpty()) {
            usernameView.text = getString(R.string.welcome_user_label, username)
        } else {
            usernameView.text = getString(R.string.welcome_user_default_label)
        }
    }
}