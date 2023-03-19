package com.bruno13palhano.jaspe.ui.home

import com.bruno13palhano.model.Banner
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.model.Product

data class HomeUiState (
    val allProducts: List<Product> = emptyList(),
    val amazonProducts: List<Product> = emptyList(),
    val naturaProducts: List<Product> = emptyList(),
    val avonProducts: List<Product> = emptyList(),
    val lastSeenProducts: List<Product> = emptyList(),
    val mainBanner: Banner = Banner(),
    val amazonBanner: Banner = Banner(),
    val naturaBanner: Banner = Banner(),
    val avonBanner: Banner = Banner(),
    val contactInfo: ContactInfo = ContactInfo(),
    val username: String = "",
    val profileUrlPhoto: String = "",
    val notificationCount: Int = 0
)