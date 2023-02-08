package com.bruno13palhano.jaspe.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.ViewModelFactory
import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.model.Product
import kotlinx.coroutines.launch

class MockProductFragment : Fragment() {
    private var NUM_PAGES = 1
    private lateinit var viewPager: ViewPager2
    private var urlLinks = mutableSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mock_product, container, false)
        viewPager = view.findViewById(R.id.pager)

        val productUrlLink = MockProductFragmentArgs.fromBundle(requireArguments())
            .productUrlLink
        val productType = MockProductFragmentArgs.fromBundle(requireArguments())
            .productType

        val viewModel = ViewModelFactory(requireActivity(), this@MockProductFragment)
            .createMockViewModel()

        var pagerAdapter: ProductSlidePagerAdapter

        lifecycle.coroutineScope.launch {
            try {
                viewModel.getProductUrlLink(productUrlLink).collect {
                    urlLinks.add(it.productUrlLink)
                }
            } catch (ignored: Exception) {
                try {
                    viewModel.getFavoriteProductByUrlLink(productUrlLink).collect {
                        urlLinks.add(it.favoriteProductUrlLink)
                    }
                } catch (ignored: Exception) {
                    try {
                        viewModel.getProductLastSeen(productUrlLink).collect {
                            urlLinks.add(it.productUrlLink)
                        }
                    } catch (ignored: Exception) {}
                }
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.getRelatedProducts(productType).collect {
                NUM_PAGES = it.size
                it.map { product ->
                    urlLinks.add(product.productUrlLink)
                }
                pagerAdapter = ProductSlidePagerAdapter(this@MockProductFragment)
                viewPager.adapter = pagerAdapter
            }
        }

        return view
    }

    private inner class ProductSlidePagerAdapter(
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {
            return ProductSlidePageFragment(urlLinks.elementAt(position))
        }
    }
}