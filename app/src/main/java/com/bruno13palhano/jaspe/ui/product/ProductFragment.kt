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
import com.bruno13palhano.model.Product
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {
    private var productUrlLink: String = ""
    private lateinit var viewModel: ProductViewModel
    private lateinit var favoriteProduct: FavoriteProduct
    private var isFavorite = false
    private lateinit var productImage: ImageView
    private lateinit var productName: TextView
    private lateinit var productPrice: TextView
    private lateinit var productType: TextView
    private lateinit var productDescription: TextView
    private lateinit var buyByWhatsApp: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = setLayout(inflater, container)

        productImage = view.findViewById(R.id.product_image)
        productName = view.findViewById(R.id.product_name)
        productPrice = view.findViewById(R.id.product_price)
        productType = view.findViewById(R.id.product_type)
        productDescription = view.findViewById(R.id.product_description)

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

        buyByWhatsApp = view.findViewById(R.id.buy_by_whatsapp)
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
                    favoriteProduct = convertProductToFavorite(it)
                    setProductViews(it)
                    setBuyByWhatsAppVisibility(it.productCompany)
                }
            } catch (ignored: Exception) {
                try {
                    viewModel.getFavoriteProductByUrlLink(productUrlLink).collect {
                        isFavorite = true
                        favoriteProduct = it
                        viewModel.setFavoriteValue(it.favoriteProductIsVisible)
                        setFavoriteProductViews(it)
                        setBuyByWhatsAppVisibility(it.favoriteProductCompany)
                    }
                } catch (ignored: Exception) {
                    try {
                        viewModel.getProductLastSeen(productUrlLink).collect {
                            favoriteProduct = convertProductToFavorite(it)
                            setProductViews(it)
                            setBuyByWhatsAppVisibility(it.productCompany)
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

        setFavoriteIconAppearance(toolbar)

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

    private fun setLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_product_landscape, container, false)
        } else {
            inflater.inflate(R.layout.fragment_product, container, false)
        }
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

    private fun setProductViews(product: Product) {
        productImage.load(product.productUrlImage)
        productName.text = product.productName
        productPrice.text = getString(R.string.product_price_label, product.productPrice)
        productType.text = product.productType
        productDescription.text = product.productDescription
    }

    private fun setFavoriteProductViews(favoriteProduct: FavoriteProduct) {
        productImage.load(favoriteProduct.favoriteProductUrlImage)
        productName.text = favoriteProduct.favoriteProductName
        productPrice.text = getString(R.string.product_price_label,
            favoriteProduct.favoriteProductPrice)
        productType.text = favoriteProduct.favoriteProductType
        productDescription.text = favoriteProduct.favoriteProductDescription
    }

    private fun setBuyByWhatsAppVisibility(company: String) {
        if (company == Company.AMAZON.company) {
            buyByWhatsApp.visibility = GONE
        }
    }
}