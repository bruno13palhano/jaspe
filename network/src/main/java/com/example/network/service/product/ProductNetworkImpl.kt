package com.example.network.service.product

import com.bruno13palhano.model.Product
import com.example.network.model.asProduct
import com.example.network.service.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProductNetworkImpl @Inject constructor(
    private val apiService: Service
) : ProductNetwork {

    override suspend fun getProducts(params: List<Int>): Flow<List<Product>> = flow {
        try {
            emit(apiService.getProducts(params).map {
                it.asProduct()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override suspend fun getProductById(productId: Long): Flow<Product> = flow {
        try {
            emit(apiService.getProductById(productId).asProduct())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAmazonProducts(): Flow<List<Product>> = flow {
        try {
            emit(
                apiService.getAmazonProducts().map {
                    it.asProduct()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getNaturaProducts(): Flow<List<Product>> = flow {
        try {
            emit(
                apiService.getNaturaProducts().map {
                    it.asProduct()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAvonProducts(): Flow<List<Product>> = flow {
        try {
            emit(
                apiService.getAvonProducts().map {
                    it.asProduct()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}