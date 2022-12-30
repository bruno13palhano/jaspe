package com.bruno13palhano.jaspe.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.forEach
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.model.Product
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.home_list)

        val adapter = HomeItemAdapter {
            val action = HomeFragmentDirections.actionHomeToProduct(it)
            view.findNavController().navigate(action)
        }

        val viewModel = activity?.applicationContext?.let {
            ViewModelFactory(it, this@HomeFragment).createHomeViewModel()
        }

        var idList: List<Long> = emptyList()

//        lifecycle.coroutineScope.launch {
//            viewModel?.insertProduct(
//                Product(
//                    productName = "Desodorante Antitranspirante",
//                    productUrlImage = R.drawable.perfume_6.toString(),
//                    productPrice = 24.90f,
//                    productType = "Desodorante",
//                    productDescription = "Roll-On Tododia Leite de Algodão",
//                    productCompany = "Natura",
//                    productUrlLink = "https://www.natura.com.br/p/desodorante-antitranspirante-roll-on-tododia-leite-de-algodao-70-ml/69669?consultoria=default&listTitle=search%20results%20list%20showcase%20-%20desodorante%20antitranspirante&position=1",
//                    productIsFavorite = false
//                )
//            )
//        }

        lifecycle.coroutineScope.launch {
            viewModel?.getAllProducts()?.collect {
                setViews(view, it)
                adapter.submitList(it)
                idList = it.map { product -> product.productId }
            }
        }

        recyclerView.adapter = adapter

        val grid1 = view.findViewById<GridLayout>(R.id.grid_1)
        grid1.forEach {
            it.setOnClickListener { view ->
                try {
                    val action: NavDirections
                    when (view.id) {
                        R.id.offers_card_1 -> {
                            idList[0].let {
                                action = HomeFragmentDirections.actionHomeToProduct(idList[0])
                                view.findNavController().navigate(action)
                            }
                        }
                        R.id.offers_card_2 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[1])
                            view.findNavController().navigate(action)
                        }
                        R.id.offers_card_3 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[2])
                            view.findNavController().navigate(action)
                        }
                        R.id.offers_card_4 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[3])
                            view.findNavController().navigate(action)
                        }
                        R.id.offers_card_5 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[4])
                            view.findNavController().navigate(action)
                        }
                        R.id.offers_card_6 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[5])
                            view.findNavController().navigate(action)
                        }
                    }
                } catch (ignored: IndexOutOfBoundsException) { }
            }
        }

        val grid2 = view.findViewById<GridLayout>(R.id.grid_2)
        grid2.forEach {
            it.setOnClickListener { view ->
                try {
                    val action: NavDirections
                    when (view.id) {
                        R.id.natura_card_1 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[0])
                            view.findNavController().navigate(action)
                        }
                        R.id.natura_card_2 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[1])
                            view.findNavController().navigate(action)
                        }
                        R.id.natura_card_3 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[2])
                            view.findNavController().navigate(action)
                        }
                        R.id.natura_card_4 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[3])
                            view.findNavController().navigate(action)
                        }
                        R.id.natura_card_5 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[4])
                            view.findNavController().navigate(action)
                        }
                        R.id.natura_card_6 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[5])
                            view.findNavController().navigate(action)
                        }
                    }
                } catch (ignored: IndexOutOfBoundsException) {}
            }
        }

        val grid3 = view.findViewById<GridLayout>(R.id.grid_3)
        grid3.forEach {
            it.setOnClickListener { view ->
                try {
                    val action: NavDirections
                    when (view.id) {
                        R.id.avon_card_1 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[0])
                            view.findNavController().navigate(action)
                        }
                        R.id.avon_card_2 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[1])
                            view.findNavController().navigate(action)
                        }
                        R.id.avon_card_3 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[2])
                            view.findNavController().navigate(action)
                        }
                        R.id.avon_card_4 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[3])
                            view.findNavController().navigate(action)
                        }
                        R.id.avon_card_5 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[4])
                            view.findNavController().navigate(action)
                        }
                        R.id.avon_card_6 -> {
                            action = HomeFragmentDirections.actionHomeToProduct(idList[5])
                            view.findNavController().navigate(action)
                        }
                    }
                } catch (ignored: IndexOutOfBoundsException) {}
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.show()
    }

    private fun setViews(view: View, productList: List<Product>) {
        val images = getImagesView(view)
        val names = getNamesView(view)
        val prices = getPricesView(view)
        val types = getTypesView(view)

        setValues(
            images = images,
            names = names,
            prices = prices,
            types = types,
            productList = productList
        )
    }

    private fun setValues(
        images: Array<ImageView>,
        names: Array<TextView>,
        prices: Array<TextView>,
        types: Array<TextView>,
        productList: List<Product>
    ) {
        for (i in productList.indices) {
            images[i].setImageResource(productList[i].productUrlImage.toInt())
            names[i].text = productList[i].productName
            prices[i].text = productList[i].productPrice.toString()
            types[i].text = productList[i].productType
        }
    }

    private fun getImagesView(view: View): Array<ImageView> {
        return arrayOf(
            view.findViewById(R.id.offers_product_image_1),
            view.findViewById(R.id.offers_product_image_2),
            view.findViewById(R.id.offers_product_image_3),
            view.findViewById(R.id.offers_product_image_4),
            view.findViewById(R.id.offers_product_image_5),
            view.findViewById(R.id.offers_product_image_6),

            view.findViewById(R.id.natura_product_image_1),
            view.findViewById(R.id.natura_product_image_2),
            view.findViewById(R.id.natura_product_image_3),
            view.findViewById(R.id.natura_product_image_4),
            view.findViewById(R.id.natura_product_image_5),
            view.findViewById(R.id.natura_product_image_6),

            view.findViewById(R.id.avon_product_image_1),
            view.findViewById(R.id.avon_product_image_2),
            view.findViewById(R.id.avon_product_image_3),
            view.findViewById(R.id.avon_product_image_4),
            view.findViewById(R.id.avon_product_image_5),
            view.findViewById(R.id.avon_product_image_6)
        )
    }

    private fun getNamesView(view: View): Array<TextView> {
        return arrayOf(
            view.findViewById(R.id.offers_product_name_1),
            view.findViewById(R.id.offers_product_name_2),
            view.findViewById(R.id.offers_product_name_3),
            view.findViewById(R.id.offers_product_name_4),
            view.findViewById(R.id.offers_product_name_5),
            view.findViewById(R.id.offers_product_name_6),

            view.findViewById(R.id.natura_product_name_1),
            view.findViewById(R.id.natura_product_name_2),
            view.findViewById(R.id.natura_product_name_3),
            view.findViewById(R.id.natura_product_name_4),
            view.findViewById(R.id.natura_product_name_5),
            view.findViewById(R.id.natura_product_name_6),

            view.findViewById(R.id.avon_product_name_1),
            view.findViewById(R.id.avon_product_name_2),
            view.findViewById(R.id.avon_product_name_3),
            view.findViewById(R.id.avon_product_name_4),
            view.findViewById(R.id.avon_product_name_5),
            view.findViewById(R.id.avon_product_name_6),
        )
    }

    private fun getPricesView(view: View): Array<TextView> {
        return arrayOf(
            view.findViewById(R.id.offers_product_price_1),
            view.findViewById(R.id.offers_product_price_2),
            view.findViewById(R.id.offers_product_price_3),
            view.findViewById(R.id.offers_product_price_4),
            view.findViewById(R.id.offers_product_price_5),
            view.findViewById(R.id.offers_product_price_6),

            view.findViewById(R.id.natura_product_price_1),
            view.findViewById(R.id.natura_product_price_2),
            view.findViewById(R.id.natura_product_price_3),
            view.findViewById(R.id.natura_product_price_4),
            view.findViewById(R.id.natura_product_price_5),
            view.findViewById(R.id.natura_product_price_6),

            view.findViewById(R.id.avon_product_price_1),
            view.findViewById(R.id.avon_product_price_2),
            view.findViewById(R.id.avon_product_price_3),
            view.findViewById(R.id.avon_product_price_4),
            view.findViewById(R.id.avon_product_price_5),
            view.findViewById(R.id.avon_product_price_6)
        )
    }

    private fun getTypesView(view: View): Array<TextView> {
        return arrayOf<TextView>(
            view.findViewById(R.id.offers_product_type_1),
            view.findViewById(R.id.offers_product_type_2),
            view.findViewById(R.id.offers_product_type_3),
            view.findViewById(R.id.offers_product_type_4),
            view.findViewById(R.id.offers_product_type_5),
            view.findViewById(R.id.offers_product_type_6),

            view.findViewById(R.id.natura_product_type_1),
            view.findViewById(R.id.natura_product_type_2),
            view.findViewById(R.id.natura_product_type_3),
            view.findViewById(R.id.natura_product_type_4),
            view.findViewById(R.id.natura_product_type_5),
            view.findViewById(R.id.natura_product_type_6),

            view.findViewById(R.id.avon_product_type_1),
            view.findViewById(R.id.avon_product_type_2),
            view.findViewById(R.id.avon_product_type_3),
            view.findViewById(R.id.avon_product_type_4),
            view.findViewById(R.id.avon_product_type_5),
            view.findViewById(R.id.avon_product_type_6)
        )
    }
}