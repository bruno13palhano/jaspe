package com.bruno13palhano.jaspe.ui.product

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.model.FavoriteProduct
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {
    private var productId: Long = 0L
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

        productId = ProductFragmentArgs.fromBundle(
            requireArguments()
        ).productId
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

        lifecycle.coroutineScope.launch {
            try {
                viewModel.getFavoriteProductByUrlLink(productUrlLink).collect { favorite ->
                    if (favorite.favoriteProductIsVisible) {
                        viewModel.toggleFavorite()
                    }
                    isFavorite = true
                    favoriteProduct = favorite
                    productImage.load(favorite.favoriteProductUrlImage)
                    productName.text = favorite.favoriteProductName
                    productPrice.text = getString(R.string.product_price_label, favorite.favoriteProductPrice)
                    productType.text = favorite.favoriteProductType
                    productDescription.text = favorite.favoriteProductDescription
                }
            } catch (ignored : Exception) {}
        }

        lifecycle.coroutineScope.launch {
            viewModel.getProduct(productId).collect { product ->
                if (product.productUrlLink == productUrlLink) {
                    favoriteProduct = FavoriteProduct(
                        favoriteProductId = 0L,
                        favoriteProductName = product.productName,
                        favoriteProductUrlImage = product.productUrlImage,
                        favoriteProductPrice = product.productPrice,
                        favoriteProductType = product.productType,
                        favoriteProductDescription = product.productDescription,
                        favoriteProductUrlLink = product.productUrlLink,
                        favoriteProductIsVisible = true
                    )
                    productImage.load(product.productUrlImage)
                    productName.text = product.productName
                    productPrice.text = getString(R.string.product_price_label, product.productPrice)
                    productType.text = product.productType
                    productDescription.text = product.productDescription
                }
            }
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
                    lifecycle.coroutineScope.launch {
                        launch {
                            if (isFavorite) {
                                viewModel.setFavoriteVisibility(
                                    favoriteProduct.favoriteProductId, !favoriteProduct.favoriteProductIsVisible)
                            } else {
                                viewModel.addFavoriteProduct(favoriteProduct)
                            }
                        }

                        viewModel.toggleFavorite()
                    }

                    true
                }
                R.id.shareProduct -> {
                    val shareProductLink = Intent.createChooser(Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/*"
                        putExtra(Intent.EXTRA_TEXT, favoriteProduct.favoriteProductUrlLink)
                        putExtra(Intent.EXTRA_TITLE, favoriteProduct.favoriteProductName)

                    }, null)
                    startActivity(shareProductLink)

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