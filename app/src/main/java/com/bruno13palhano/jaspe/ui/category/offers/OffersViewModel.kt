package com.bruno13palhano.jaspe.ui.category.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bruno13palhano.jaspe.ui.common.getOrderedProducts
import com.bruno13palhano.jaspe.ui.common.prepareLastSeenProduct
import com.bruno13palhano.jaspe.ui.search.FilterType
import com.bruno13palhano.model.Company
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.di.DefaultProductRepository
import com.bruno13palhano.repository.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    @DefaultProductRepository
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts = _allProducts.asStateFlow()

    init {
        viewModelScope.launch {
            productRepository.getByCompany(Company.AMAZON.company, 0, 20).collect {
                _allProducts.value = it
            }
        }
    }

    fun getOrderedProducts(filter: FilterType) {
        getOrderedProducts(_allProducts, filter)
    }

    suspend fun onProductItemClick(navController: NavController, product: Product) {
        insertLastSeenProduct(product)
        OffersSimpleStateHolder.navigateToProduct(
            navController = navController,
            productUrlLink = product.productUrlLink,
            productType = product.productType
        )
    }

    private suspend fun insertLastSeenProduct(product: Product) {
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