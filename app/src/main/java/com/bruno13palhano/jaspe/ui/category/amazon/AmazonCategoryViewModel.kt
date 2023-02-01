package com.bruno13palhano.jaspe.ui.category.amazon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Type
import com.bruno13palhano.repository.external.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AmazonCategoryViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts = _allProducts.asStateFlow()

    init {
        viewModelScope.launch {
            productRepository.getByType(Type.MARKET.type).collect {
                _allProducts.value = it
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
}