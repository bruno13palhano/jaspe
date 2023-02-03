package com.bruno13palhano.jaspe.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
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

    fun getOrderProducts(filter: FilterType) {
        when (filter) {
            FilterType.DEFAULT -> {
                _searchProducts.value = _searchProducts.value.sortedBy { it.productId }
            }
            FilterType.NAME -> {
                _searchProducts.value = _searchProducts.value.sortedBy { it.productName }
            }
            FilterType.LOW_PRICE -> {
                _searchProducts.value = _searchProducts.value.sortedBy { it.productPrice }
            }
            FilterType.HIGH_PRICE -> {
                _searchProducts.value = _searchProducts.value.sortedByDescending { it.productPrice }
            }
        }
    }

    suspend fun insertLastSeenProduct(product: Product) {
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