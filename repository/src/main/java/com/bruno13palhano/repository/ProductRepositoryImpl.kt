package com.bruno13palhano.repository

import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.dao.ProductDao
import com.bruno13palhano.repository.util.convertProductRepToProduct
import com.bruno13palhano.repository.util.convertProductToProductRep
import com.example.network.service.ProductNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProductRepositoryImpl(
    private val dao: ProductDao,
    private val network: ProductNetwork
) : ProductRepository {
    override suspend fun insertProduct(product: Product) {
        dao.insert(convertProductToProductRep(product))
    }

    override suspend fun updateProduct(product: Product) {
        dao.update(convertProductToProductRep(product))
    }

    override suspend fun deleteProduct(product: Product) {
        dao.delete(convertProductToProductRep(product))
    }

    override suspend fun deleteProductById(productId: Long) {
        dao.deleteById(productId)
    }
    override fun get(productId: Long): Flow<Product> {
        return dao.get(productId).map {
            convertProductRepToProduct(it)
        }
    }

    override fun getAll(): Flow<List<Product>> {
        return dao.getAll().map {
            it.map { productRep ->
                convertProductRepToProduct(productRep)
            }
        }
    }

    override fun getAllFavorites(): Flow<List<Product>> {
        return dao.getAllFavorites().map {
            it.map { productRep ->
                convertProductRepToProduct(productRep)
            }
        }
    }

    override fun getByCompany(
        productCompany: String,
        offset: Int,
        limit: Int
    ): Flow<List<Product>> {
        return dao.getByCompany(productCompany, offset, limit)
            .map {
                it.map { productRep ->
                    convertProductRepToProduct(productRep)
                }
            }
    }

    override suspend fun getProductById(productId: Long): Flow<Product> {
        return network.getProductById(productId)
    }

    override suspend fun getNaturaProducts(params: List<Int>): Flow<List<Product>> {
        return network.getProducts(params)
    }
}