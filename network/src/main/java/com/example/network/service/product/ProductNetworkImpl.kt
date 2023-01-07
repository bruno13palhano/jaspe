package com.example.network.service.product

import com.bruno13palhano.model.Product
import com.example.network.service.ApiService
import com.example.network.util.convertProductNetToProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ProductNetworkImpl : ProductNetwork {

    override suspend fun getProducts(params: List<Int>): Flow<List<Product>> = flow {
        try {
            emit(ApiService.ProductApi.apiService.getProducts(params).map {
                convertProductNetToProduct(it)
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override suspend fun getProductById(productId: Long): Flow<Product> = flow {
        try {
            emit(
                convertProductNetToProduct(
                    ApiService.ProductApi.apiService.getProductById(
                        productId
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAmazonProducts(): Flow<List<Product>> = flow {
        try {
            emit(
                ApiService.ProductApi.apiService.getAmazonProducts().map {
                    convertProductNetToProduct(it)
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getNaturaProducts(): Flow<List<Product>> = flow {
        try {
            emit(
                ApiService.ProductApi.apiService.getNaturaProducts().map {
                    convertProductNetToProduct(it)
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAvonProducts(): Flow<List<Product>> = flow {
        try {
            emit(
                ApiService.ProductApi.apiService.getAvonProducts().map {
                    convertProductNetToProduct(it)
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}