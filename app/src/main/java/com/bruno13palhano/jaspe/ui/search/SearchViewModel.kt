package com.bruno13palhano.jaspe.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _searchProducts = MutableStateFlow<List<Product>>(emptyList())
    val searchProducts: StateFlow<List<Product>> = _searchProducts

    fun searchProductByTitle(productName: String) {
        if (productName != "") {
            viewModelScope.launch {
                productRepository.searchProductByTitle(productName).collect {
                    _searchProducts.value = it
                }
            }
        }
    }
}