package com.bruno13palhano.repository

import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.dao.ProductDao
import com.bruno13palhano.repository.model.asProduct
import com.bruno13palhano.repository.model.asProductRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProductRepositoryImpl(
    private val dao: ProductDao
) : ProductRepository {
    override suspend fun insertProduct(product: Product) {
        dao.insert(product.asProductRep())
    }

    override suspend fun insertProducts(productList: List<Product>) {
        dao.insertAll(productList.map {
             it.asProductRep()
        })
    }

    override suspend fun updateProduct(product: Product) {
        dao.update(product.asProductRep())
    }

    override suspend fun deleteProduct(product: Product) {
        dao.delete(product.asProductRep())
    }

    override suspend fun deleteProductById(productId: Long) {
        dao.deleteById(productId)
    }

    override fun get(productId: Long): Flow<Product> {
        return dao.get(productId).map {
            it.asProduct()
        }
    }

    override fun getByLink(productLink: String): Flow<Product> {
        return dao.getByLink(productLink).map {
            it.asProduct()
        }
    }

    override fun getAll(): Flow<List<Product>> {
        return dao.getAll().map {
            it.map { productRep ->
                productRep.asProduct()
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
                    productRep.asProduct()
                }
            }
    }

    override fun getByType(productType: String): Flow<List<Product>> {
        return dao.getByType(productType).map {
            it.map { productRep ->
                productRep.asProduct()
            }
        }
    }

    override fun searchProduct(productName: String): Flow<List<Product>> {
        return dao.searchProduct(productName).map {
            it.map { productRep ->
                productRep.asProduct()
            }
        }
    }

    override fun getProductByLink(productUrlLink: String): Flow<Product> {
        return dao.getProductByLink(productUrlLink).map {
            it.asProduct()
        }
    }
}