package com.example.network.service

class ProductNetworkFactory {

    fun createProductNetWork(): ProductNetwork {
        return ProductNetworkImpl()
    }
}