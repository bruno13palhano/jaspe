package com.bruno13palhano.jaspe.ui.home

import com.bruno13palhano.model.Product

data class HomeProductsUiState(
    val products: List<Product> = emptyList(),
    val amazonProducts: List<Product> = emptyList(),
    val naturaProducts: List<Product> = emptyList(),
    val avonProducts: List<Product> = emptyList(),
    val lastSeenProducts: List<Product> = emptyList()
)
