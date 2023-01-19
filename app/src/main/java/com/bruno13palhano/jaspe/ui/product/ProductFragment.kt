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
        var url = "http://www.google.com"

        val buyButton = view.findViewById<ExtendedFloatingActionButton>(R.id.buy_product_button)
        buyButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        productId = ProductFragmentArgs.fromBundle(
            requireArguments()
        ).productId

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@ProductFragment).createProductViewModel()
        }

        lifecycle.coroutineScope.launch {
            viewModel?.getProduct(productId).let {
                it?.collect { product ->
                    productImage.load(product.productUrlImage)
                    productName.text = product.productName
                    productPrice.text = getString(R.string.product_price_label, product.productPrice)
                    productType.text = product.productType
                    productDescription.text = product.productDescription
                    url = product.productUrlLink
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

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@ProductFragment).createProductViewModel()
        }

        var favoriteProduct = FavoriteProduct(
            0L,
            "",
            "",
            0f,
            "",
            ""
        )
        lifecycle.coroutineScope.launch {
            viewModel?.getProduct(productId)?.collect {
                favoriteProduct = FavoriteProduct(
                    favoriteProductId = it.productId,
                    favoriteProductName = it.productName,
                    favoriteProductUrlImage = it.productUrlImage,
                    favoriteProductPrice = it.productPrice,
                    favoriteProductType = it.productType,
                    favoriteProductUrlLink = it.productUrlLink
                )
            }
        }

        lifecycle.coroutineScope.launch {
            try {
                viewModel?.getFavoriteProduct(productId)?.collect {
                    if (it.favoriteProductId != 0L) {
                        viewModel.toggleFavorite()
                    }
                }
            } catch (ignored: Exception) {}
        }

        lifecycle.coroutineScope.launch {
            viewModel?.isFavorite?.collect {
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
                        viewModel?.isFavorite?.value?.let { isFavorite ->
                            if (!isFavorite) {
                                val favorite = FavoriteProduct(
                                    favoriteProductId = productId,
                                    favoriteProductName = favoriteProduct.favoriteProductName,
                                    favoriteProductUrlImage = favoriteProduct.favoriteProductUrlImage,
                                    favoriteProductType = favoriteProduct.favoriteProductType,
                                    favoriteProductPrice = favoriteProduct.favoriteProductPrice,
                                    favoriteProductUrlLink = favoriteProduct.favoriteProductUrlLink,
                                )

                                viewModel.toggleFavorite()
                                viewModel.addFavoriteProduct(favorite)
                            } else {
                                viewModel.toggleFavorite()
                                viewModel.removeFavoriteProduct(productId)
                            }
                        }
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