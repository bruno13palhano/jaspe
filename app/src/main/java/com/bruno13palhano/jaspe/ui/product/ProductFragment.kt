package com.bruno13palhano.jaspe.ui.product

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.Company
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Type
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {
    private var productUrlLink: String = ""
    private lateinit var viewModel: ProductViewModel
    private lateinit var favoriteProduct: FavoriteProduct
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_product_landscape, container, false)
        } else {
            inflater.inflate(R.layout.fragment_product, container, false)
        }

        val productImage = view.findViewById<ImageView>(R.id.product_image)
        val productName = view.findViewById<TextView>(R.id.product_name)
        val productPrice = view.findViewById<TextView>(R.id.product_price)
        val productType = view.findViewById<TextView>(R.id.product_type)
        val productDescription = view.findViewById<TextView>(R.id.product_description)

        productUrlLink = ProductFragmentArgs.fromBundle(
            requireArguments()
        ).productUrlLink

        val buyButton = view.findViewById<ExtendedFloatingActionButton>(R.id.buy_product_button)
        buyButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(productUrlLink)))
        }

        viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@ProductFragment).createProductViewModel()
        }!!

        val buyByWhatsApp = view.findViewById<CardView>(R.id.buy_by_whatsapp)
        buyByWhatsApp.setOnClickListener {
            lifecycle.coroutineScope.launch {
                viewModel.contactWhatsApp.collect { whatsApp ->
                    openWhatsApp(
                        this@ProductFragment.requireContext(), whatsApp,
                        favoriteProduct.favoriteProductUrlLink
                    )
                }
            }
        }

        lifecycle.coroutineScope.launch {
            try {
                viewModel.getProductByUrlLink(productUrlLink).collect {
                    favoriteProduct = FavoriteProduct(
                        favoriteProductId = 0L,
                        favoriteProductUrlImage = it.productUrlImage,
                        favoriteProductName = it.productName,
                        favoriteProductPrice = it.productPrice,
                        favoriteProductUrlLink = it.productUrlLink,
                        favoriteProductType = it.productType,
                        favoriteProductDescription = it.productDescription,
                        favoriteProductCompany = it.productCompany,
                        favoriteProductIsVisible = true
                    )
                    productImage.load(it.productUrlImage)
                    productName.text = it.productName
                    productPrice.text = getString(R.string.product_price_label, it.productPrice)
                    productType.text = it.productType
                    productDescription.text = it.productDescription

                    if (it.productCompany == Company.AMAZON.company) {
                        buyByWhatsApp.visibility = GONE
                    }
                }
            } catch (ignored: Exception) {
                try {
                    viewModel.getFavoriteProductByUrlLink(productUrlLink).collect {
                        isFavorite = true
                        favoriteProduct = it
                        viewModel.setFavoriteValue(it.favoriteProductIsVisible)
                        productImage.load(it.favoriteProductUrlImage)
                        productName.text = it.favoriteProductName
                        productPrice.text = getString(R.string.product_price_label, it.favoriteProductPrice)
                        productType.text = it.favoriteProductType
                        productDescription.text = it.favoriteProductDescription

                        if (it.favoriteProductType == Type.MARKET.type ||
                                it.favoriteProductType == Type.BABY.type) {
                            buyByWhatsApp.visibility = GONE
                        }
                    }
                } catch (ignored: Exception) {
                    try {
                        viewModel.getProductLastSeen(productUrlLink).collect {
                            favoriteProduct = FavoriteProduct(
                                favoriteProductId = 0L,
                                favoriteProductUrlImage = it.productUrlImage,
                                favoriteProductName = it.productName,
                                favoriteProductPrice = it.productPrice,
                                favoriteProductUrlLink = it.productUrlLink,
                                favoriteProductType = it.productType,
                                favoriteProductDescription = it.productDescription,
                                favoriteProductCompany = it.productCompany,
                                favoriteProductIsVisible = true
                            )
                            productImage.load(it.productUrlImage)
                            productName.text = it.productName
                            productPrice.text = getString(R.string.product_price_label, it.productPrice)
                            productType.text = it.productType
                            productDescription.text = it.productDescription

                            if (it.productCompany == Company.AMAZON.company) {
                                buyByWhatsApp.visibility = GONE
                            }
                        }
                    } catch (ignored: Exception) {}
                }
            }
        }

        lifecycle.coroutineScope.launch {
            try {
                viewModel.getFavoriteProductByUrlLink(productUrlLink).collect {
                    isFavorite = true
                    favoriteProduct = it
                    viewModel.setFavoriteValue(it.favoriteProductIsVisible)
                }
            } catch (ignored: Exception) {}
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_product)
        toolbar.inflateMenu(R.menu.menu_toolbar_product)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        lifecycle.coroutineScope.launch {
            viewModel.isFavorite.collect {
                if (it) {
                    toolbar.menu.getItem(0).icon?.setTint(resources.getColor(R.color.pink_light))
                } else {
                    toolbar.menu.getItem(0).icon?.setTint(resources.getColor(R.color.black))
                }
            }
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favoriteProduct -> {
                    viewModel.setFavorite(isFavorite, favoriteProduct)
                    true
                }
                R.id.shareProduct -> {
                    viewModel.shareProduct(this@ProductFragment.requireContext(), favoriteProduct)
                    true
                }
                else -> false
            }
        }

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}