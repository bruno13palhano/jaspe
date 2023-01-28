package com.bruno13palhano.repository.external

import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.dao.ProductDao
import com.bruno13palhano.repository.model.asProduct
import com.bruno13palhano.repository.model.asProductRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProductRepositoryImpl(
    private val dao: ProductDao
) : ProductRepository {
    override suspend fun insertProducts(productList: List<Product>) {
        dao.insertAll(productList.map {
             it.asProductRep()
        })
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

    override fun getProductLastSeen(offset: Int, limit: Int): Flow<List<Product>> {
        return dao.getLastSeen(offset, limit).map {
            it.map { productRep ->
                productRep.asProduct()
            }
        }
    }

    override suspend fun updateSeenValue(productUrlLink: String, productSeenNewValue: Long) {
        return dao.updateSeenValue(productUrlLink, productSeenNewValue)
    }
}