package com.example.network.service

class NetworkFactory {

    fun createProductNetWork(): ProductNetwork {
        return ProductNetworkImpl()
    }
}