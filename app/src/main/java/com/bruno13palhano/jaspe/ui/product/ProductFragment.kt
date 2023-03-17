package com.bruno13palhano.jaspe.ui.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.databinding.FragmentProductBinding
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.Company
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private var productUrlLink: String = ""
    private var productTypeArg: String = ""
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var favoriteProduct: FavoriteProduct
    private var isFavorite = false
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val view = binding.root

        productUrlLink = ProductFragmentArgs.fromBundle(requireArguments()).productUrlLink
        productTypeArg = ProductFragmentArgs.fromBundle(requireArguments()).productType

        binding.buyProductButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(productUrlLink)))
        }

        binding.buyByWhatsapp.setOnClickListener {
            lifecycle.coroutineScope.launch {
                viewModel.contactWhatsApp.collect { whatsApp ->
                    openWhatsApp(
                        this@ProductFragment.requireContext(), whatsApp,
                        favoriteProduct.favoriteProductUrlLink
                    )
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarProduct.inflateMenu(R.menu.menu_toolbar_product)
        binding.toolbarProduct.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        setFavoriteIconAppearance(binding.toolbarProduct)

        binding.toolbarProduct.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favoriteProduct -> {
                    viewModel.setFavorite(isFavorite, favoriteProduct)
                    true
                }
                R.id.shareProduct -> {
                    ProductUtil.shareProduct(this@ProductFragment.requireContext(), favoriteProduct)
                    true
                }
                else -> false
            }
        }

        binding.toolbarProduct.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getProductByUrlLink(productUrlLink).collect {
                        favoriteProduct = ProductUtil.convertProductToFavorite(it)
                        setProductViews(it)
                        setBuyByWhatsAppVisibility(it.productCompany)
                    }
                }
            } catch (ignored: Exception) {
                try {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.getFavoriteProductByUrlLink(productUrlLink).collect {
                            isFavorite = true
                            favoriteProduct = it
                            viewModel.setFavoriteValue(it.favoriteProductIsVisible)
                            setFavoriteProductViews(it)
                            setBuyByWhatsAppVisibility(it.favoriteProductCompany)
                        }
                    }
                } catch (ignored: Exception) {
                    try {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.getProductLastSeen(productUrlLink).collect {
                                favoriteProduct = ProductUtil.convertProductToFavorite(it)
                                setProductViews(it)
                                setBuyByWhatsAppVisibility(it.productCompany)
                            }
                        }
                    } catch (ignored: Exception) {}
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getFavoriteProductByUrlLink(productUrlLink).collect {
                        isFavorite = true
                        favoriteProduct = it
                        viewModel.setFavoriteValue(it.favoriteProductIsVisible)
                    }
                }
            } catch (ignored: Exception) {}
        }

        val relatedProductsAdapter = RelatedProductItemAdapter {
            favoriteProduct = ProductUtil.convertProductToFavorite(it)
            setProductViews(it)
            productUrlLink = it.productUrlLink
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.getFavoriteProductByUrlLink(productUrlLink).collect { favorite ->
                            isFavorite = true
                            viewModel.setFavoriteValue(favorite.favoriteProductIsVisible)
                        }
                    }
                } catch (e: Exception) {
                    isFavorite = false
                    viewModel.setFavoriteValue(false)
                }
            }
        }
        binding.relatedProductsRecyclerView.adapter = relatedProductsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getRelatedProducts(productTypeArg).collect {
                        relatedProductsAdapter.submitList(it)
                    }
                }
            } catch (ignored: Exception) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setFavoriteIconAppearance(toolbar: MaterialToolbar) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFavorite.collect {
                    if (it) {
                        toolbar.menu.getItem(0).icon?.setTint(resources.getColor(R.color.pink_light))
                    } else {
                        toolbar.menu.getItem(0).icon?.setTint(resources.getColor(R.color.black))
                    }
                }
            }
        }
    }

    private fun setProductViews(product: Product) {
        binding.productImage.load(product.productUrlImage)
        binding.productName.text = product.productName
        binding.productPrice.text = getString(R.string.product_price_label, product.productPrice)
        binding.productType.text = product.productType
        binding.productDescription.text = product.productDescription
    }

    private fun setFavoriteProductViews(favoriteProduct: FavoriteProduct) {
        binding.productImage.load(favoriteProduct.favoriteProductUrlImage)
        binding.productName.text = favoriteProduct.favoriteProductName
        binding.productPrice.text = getString(R.string.product_price_label,
            favoriteProduct.favoriteProductPrice)
        binding.productType.text = favoriteProduct.favoriteProductType
        binding.productDescription.text = favoriteProduct.favoriteProductDescription
    }

    private fun setBuyByWhatsAppVisibility(company: String) {
        if (company == Company.AMAZON.company) {
            binding.buyByWhatsapp.visibility = GONE
        }
    }
}