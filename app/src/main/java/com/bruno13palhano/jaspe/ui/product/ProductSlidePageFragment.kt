package com.bruno13palhano.jaspe.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class ProductSlidePageFragment(private val urlLink: String) : Fragment() {
    private lateinit var viewModel: ProductSlidePageViewModel
    private lateinit var productName: TextView
    private lateinit var productImage: ImageView
    private lateinit var productPrice: TextView
    private lateinit var productType: TextView
    private lateinit var productDescription: TextView
    private var isFavorite = false
    private lateinit var favoriteProduct: FavoriteProduct

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_slide_page, container, false)
        productName = view.findViewById<TextView>(R.id.product_name)
        productImage = view.findViewById<ImageView>(R.id.product_image)
        productPrice = view.findViewById<TextView>(R.id.product_price)
        productType = view.findViewById<TextView>(R.id.product_type)
        productDescription = view.findViewById<TextView>(R.id.product_description)

        viewModel = ViewModelFactory(requireActivity(), this@ProductSlidePageFragment)
            .createProductSlidePageViewModel()

        lifecycle.coroutineScope.launch {
            try {
                viewModel.getFavoriteProductByUrlLink(urlLink).collect {
                    isFavorite = true
                    favoriteProduct = it
                    viewModel.setFavoriteValue(it.favoriteProductIsVisible)
                    setFavoriteProductView(it)
                }
            } catch (ignored: Exception) {
                try {
                    viewModel.getProductByUrlLink(urlLink).collect {
                        favoriteProduct = convertProductToFavorite(it)
                        setProductView(it)
                    }
                } catch (ignored: Exception) {
                    viewModel.getProductLastSeen(urlLink).collect {
                        favoriteProduct = convertProductToFavorite(it)
                        setProductView(it)
                    }
                }
            }

        }

        lifecycle.coroutineScope.launch {
            try {
                viewModel.getFavoriteProductByUrlLink(urlLink).collect {
                    isFavorite = true
                    viewModel.setFavoriteValue(it.favoriteProductIsVisible)
                }
            } catch (ignored: Exception) {}
        }

        val buyButton = view.findViewById<ExtendedFloatingActionButton>(R.id.buy_product_button)
        buyButton.setOnClickListener {
            println("urlLink: $urlLink")
        }

        val buyByWhatsApp = view.findViewById<MaterialCardView>(R.id.buy_by_whatsapp)
        buyByWhatsApp.setOnClickListener {
            println("bu by WhatsApp was clicked")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_slide_product)
        toolbar.inflateMenu(R.menu.menu_toolbar_product)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favoriteProduct -> {
                    viewModel.setFavorite(isFavorite, favoriteProduct)
                    true
                }
                R.id.shareProduct -> {
                    true
                }
                else -> false
            }
        }

        setFavoriteIconAppearance(toolbar)

        toolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setFavoriteIconAppearance(toolbar: MaterialToolbar) {
        lifecycle.coroutineScope.launch {
            viewModel.isFavorite.collect {
                if (it) {
                    toolbar.menu.getItem(0).icon?.setTint(resources.getColor(R.color.pink_light))
                } else {
                    toolbar.menu.getItem(0).icon?.setTint(resources.getColor(R.color.black))
                }
            }
        }
    }

    private fun setProductView(product: Product) {
        productName.text = product.productName
        productImage.load(product.productUrlImage)
        productPrice.text = getString(R.string.product_price_label, product.productPrice)
        productType.text = product.productType
        productDescription.text = product.productDescription
    }

    private fun setFavoriteProductView(favoriteProduct: FavoriteProduct) {
        productName.text = favoriteProduct.favoriteProductName
        productImage.load(favoriteProduct.favoriteProductUrlImage)
        productPrice.text = getString(R.string.product_price_label, favoriteProduct.favoriteProductPrice)
        productType.text = favoriteProduct.favoriteProductType
        productDescription.text = favoriteProduct.favoriteProductDescription
    }

    private fun convertProductToFavorite(product: Product): FavoriteProduct {
        return FavoriteProduct(
            favoriteProductId = 0L,
            favoriteProductUrlImage = product.productUrlImage,
            favoriteProductName = product.productName,
            favoriteProductPrice = product.productPrice,
            favoriteProductUrlLink = product.productUrlLink,
            favoriteProductType = product.productType,
            favoriteProductDescription = product.productDescription,
            favoriteProductCompany = product.productCompany,
            favoriteProductIsVisible = true
        )
    }
}