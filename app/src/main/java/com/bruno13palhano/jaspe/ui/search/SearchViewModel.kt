package com.bruno13palhano.jaspe.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.jaspe.ui.common.getOrderedProducts
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.di.DefaultProductRepository
import com.bruno13palhano.repository.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @DefaultProductRepository
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _searchProducts = MutableStateFlow<List<Product>>(emptyList())
    val searchProducts: StateFlow<List<Product>> = _searchProducts

    fun searchProduct(productName: String) {
        if (isNotTextEmpty(productName)) {
            viewModelScope.launch {
                productRepository.searchProduct(productName.trim()).collect {
                    _searchProducts.value = it
                }
            }
        }
    }

    fun getOrderedProducts(filter: FilterType) {
        getOrderedProducts(_searchProducts, filter)
    }

    fun insertLastSeenProduct(product: Product) {
        val lastSeenProduct = prepareLastSeenProduct(product)
        viewModelScope.launch {
            try {
                productRepository.getLastSeenProduct(lastSeenProduct.productUrlLink).collect {
                    productRepository.deleteLastSeenByUrlLink(lastSeenProduct.productUrlLink)
                }
            } catch (ignored: Exception) {
            } finally {
                productRepository.insertLastSeenProduct(lastSeenProduct)
            }
        }
    }

    private fun isNotTextEmpty(text: String): Boolean {
        return text != ""
    }
}