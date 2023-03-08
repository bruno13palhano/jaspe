package com.bruno13palhano.repository.repository.usercase

import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.database.dao.ProductDao
import com.bruno13palhano.repository.model.asLastSeenProduct
import com.bruno13palhano.repository.model.asProduct
import com.bruno13palhano.repository.model.asProductRep
import com.bruno13palhano.repository.repository.ProductRepository
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

    override suspend fun insertLastSeenProduct(product: Product) {
        dao.insertLastSeen(product.asLastSeenProduct())
    }

    override suspend fun deleteLastSeenByUrlLink(productUrlLink: String) {
        dao.deleteLastSeenByUrlLink(productUrlLink)
    }

    override fun getAllLastSeenProducts(): Flow<List<Product>> {
        return dao.getAllLastSeen().map {
            it.map { lastSeenRep ->
                lastSeenRep.asProduct()
            }
        }
    }

    override fun getLastSeenProduct(productUrlLink: String): Flow<Product> {
        return dao.getLastSeenProduct(productUrlLink).map {
            it.asProduct()
        }
    }

    override fun getLastSeenProducts(offset: Int, limit: Int): Flow<List<Product>> {
        return dao.getLastSeenProducts(offset, limit).map {
            it.map { lastSeenRep ->
                lastSeenRep.asProduct()
            }
        }
    }
}