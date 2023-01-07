package com.example.network.service

import com.example.network.service.banner.BannerNetwork
import com.example.network.service.banner.BannerNetworkImpl
import com.example.network.service.product.ProductNetwork
import com.example.network.service.product.ProductNetworkImpl

class NetworkFactory {

    fun createProductNetWork(): ProductNetwork {
        return ProductNetworkImpl()
    }

    fun createBannerNetWork(): BannerNetwork {
        return BannerNetworkImpl()
    }
}