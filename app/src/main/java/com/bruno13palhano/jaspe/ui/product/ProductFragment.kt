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
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.model.Product
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {
    private var productId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
    }

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
                    productImage.setImageResource(product.productUrlImage.toInt())
                    productName.text = product.productName
                    productPrice.text = product.productPrice.toString()
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

        var favoriteProduct = Product(
            productName = "",
            productPrice = 0f,
            productType = "",
            productDescription = "",
            productUrlImage = "",
            productCompany = "",
            productUrlLink = "",
            productIsFavorite = false
        )

        lifecycle.coroutineScope.launch {
            viewModel?.getProduct(productId)?.collect {
                favoriteProduct = it
                if (it.productIsFavorite) {
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
                        val updatedProduct = Product(
                            productId = favoriteProduct.productId,
                            productName = favoriteProduct.productName,
                            productUrlImage = favoriteProduct.productUrlImage,
                            productType = favoriteProduct.productType,
                            productPrice = favoriteProduct.productPrice,
                            productDescription = favoriteProduct.productDescription,
                            productCompany = favoriteProduct.productCompany,
                            productUrlLink = favoriteProduct.productUrlLink,
                            productIsFavorite = !favoriteProduct.productIsFavorite
                        )

                        viewModel?.updateProduct(updatedProduct)
                    }
                    true
                }
                R.id.shareProduct -> {
                    println("share was clicked")
                    true
                }
                else -> false
            }
        }

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.hide()
    }
}