package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.Banner
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.BannerRepository
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val bannerRepository: BannerRepository
) : ViewModel() {
    private val _amazonBanner = MutableStateFlow(emptyList<Banner>())
    val amazonBanner: StateFlow<List<Banner>> = _amazonBanner

    init {
        viewModelScope.launch {
            bannerRepository.getByCompany(
                "Amazon",
                0,
                1
            ).collect { banners ->
                _amazonBanner.value = banners
            }
        }
    }

    suspend fun insertProduct(product: Product) {
        productRepository.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productRepository.updateProduct(product)
    }

    fun getProduct(productId: Long): Flow<Product> {
        return productRepository.get(productId)
    }

    fun getAllProducts(): Flow<List<Product>> {
        return productRepository.getAll()
    }

    fun getAmazonProducts(): Flow<List<Product>> {
        return productRepository.getByCompany("Amazon", 0, 6)
    }

    fun getNaturaProducts(): Flow<List<Product>> {
        return productRepository.getByCompany("Natura", 0, 6)
    }

    fun getAvonProducts(): Flow<List<Product>> {
        return productRepository.getByCompany("Avon", 0, 6)
    }

    suspend fun deleteProductById(productId: Long) {
        productRepository.deleteProductById(productId)
    }
}