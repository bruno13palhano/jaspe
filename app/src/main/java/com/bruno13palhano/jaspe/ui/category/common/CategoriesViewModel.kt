package com.bruno13palhano.jaspe.ui.category.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.jaspe.ui.search.FilterType
import com.bruno13palhano.model.Company
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Route
import com.bruno13palhano.model.Type
import com.bruno13palhano.repository.external.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val productRepository: ProductRepository
): ViewModel() {
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProduct = _allProducts.asStateFlow()

    fun setProducts(route: String) {
        when (route) {
            Route.BABY.route -> {
                getBabyProducts()
            }
            Route.MARKET.route -> {
                getMarketProducts()
            }
            Route.AVON.route -> {
                getAvonProducts()
            }
            Route.NATURA.route -> {
                getNaturaProducts()
            }
            Route.OFFERS.route -> {
                getOffersProducts()
            }
            Route.LAST_SEEN.route -> {
                getLastSeenProducts()
            }
        }
    }

    private fun getBabyProducts() {
        viewModelScope.launch {
            productRepository.getByType(Type.BABY.type).collect {
                _allProducts.value = it
            }
        }
    }

    private fun getMarketProducts() {
        viewModelScope.launch {
            productRepository.getByType(Type.MARKET.type).collect {
                _allProducts.value = it
            }
        }
    }

    private fun getNaturaProducts() {
        viewModelScope.launch {
            productRepository.getByCompany(Company.NATURA.company, 0, 20).collect {
                _allProducts.value = it
            }
        }
    }

    private fun getAvonProducts() {
        viewModelScope.launch {
            productRepository.getByCompany(Company.AVON.company, 0, 20).collect {
                _allProducts.value = it
            }
        }
    }

    private fun getOffersProducts() {
        viewModelScope.launch {
            productRepository.getByCompany(Company.AMAZON.company, 0, 20).collect {
                _allProducts.value = it
            }
        }
    }

    private fun getLastSeenProducts() {
        viewModelScope.launch {
            productRepository.getLastSeenProducts(0, 20).collect {
                _allProducts.value = it
            }
        }
    }

    fun getOrderedProducts(filter: FilterType) {
        com.bruno13palhano.jaspe.ui.common.getOrderedProducts(_allProducts, filter)
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