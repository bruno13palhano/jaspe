package com.bruno13palhano.jaspe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.Banner
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.BannerRepository
import com.bruno13palhano.repository.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val bannerRepository: BannerRepository
) : ViewModel() {

    private val _amazonBanner = MutableStateFlow(Banner(0,"","",""))
    val amazonBanner: StateFlow<Banner> = _amazonBanner

    private val _naturaBanner = MutableStateFlow(Banner(0,"","",""))
    val naturaBanner: StateFlow<Banner> = _naturaBanner

    private val _avonBanner = MutableStateFlow(Banner(0,"","",""))
    val avonBanner: StateFlow<Banner> = _avonBanner

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts

    private val _amazonProducts = MutableStateFlow<List<Product>>(emptyList())
    val amazonProducts: StateFlow<List<Product>> = _amazonProducts

    private val _naturaProducts = MutableStateFlow<List<Product>>(emptyList())
    val naturaProducts: StateFlow<List<Product>> = _naturaProducts

    private val _avonProducts = MutableStateFlow<List<Product>>(emptyList())
    val avonProducts: StateFlow<List<Product>> = _avonProducts

    init {
        viewModelScope.launch {
            productRepository.getAll().collect {
                _allProducts.value = it
            }
        }

        viewModelScope.launch {
            productRepository.getByCompany("Amazon", 0, 6).collect {
                _amazonProducts.value = it
            }
        }

        viewModelScope.launch {
            productRepository.getByCompany("Natura", 0, 6).collect {
                _naturaProducts.value = it
            }
        }

        viewModelScope.launch {
            productRepository.getByCompany("Avon", 0, 6).collect {
                _avonProducts.value = it
            }
        }

        viewModelScope.launch {
            bannerRepository.getByCompany("Amazon", 0, 1).collect { banner ->
                try {
                    _amazonBanner.value = banner[0]
                } catch (ignored: IndexOutOfBoundsException) {}
            }
        }

        viewModelScope.launch {
            bannerRepository.getByCompany("Natura", 0, 1).collect() { banner ->
                try {
                    _naturaBanner.value = banner[0]
                } catch (ignored: IndexOutOfBoundsException) {}
            }
        }

        viewModelScope.launch {
            bannerRepository.getByCompany("Avon", 0, 1).collect { banner ->
                try {
                    _avonBanner.value = banner[0]
                } catch (ignored: IndexOutOfBoundsException) {}
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