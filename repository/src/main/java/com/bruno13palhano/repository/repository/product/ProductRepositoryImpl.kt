package com.bruno13palhano.repository.repository.product

import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.database.dao.ProductDao
import com.bruno13palhano.repository.model.asLastSeenProduct
import com.bruno13palhano.repository.model.asProduct
import com.bruno13palhano.repository.model.asProductRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
) : ProductRepository {
    override suspend fun insertProducts(productList: List<Product>) {
        dao.insertAll(productList.map {
             it.asProductRep()
        })
    }

    override fun getProductsStream(): Flow<List<Product>> {
        return dao.getAll().map {
            it.map { productRep ->
                productRep.asProduct()
            }
        }
    }

    override fun getProductsByCompanyStream(
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

    override fun getProductsByTypeStream(productType: String): Flow<List<Product>> {
        return dao.getByType(productType).map {
            it.map { productRep ->
                productRep.asProduct()
            }
        }
    }

    override fun searchProductsStream(productName: String): Flow<List<Product>> {
        return dao.searchProduct(productName).map {
            it.map { productRep ->
                productRep.asProduct()
            }
        }
    }

    override fun getProductByLinkStream(productUrlLink: String): Flow<Product> {
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

    override fun getAllLastSeenProductsStream(): Flow<List<Product>> {
        return dao.getAllLastSeen().map {
            it.map { lastSeenRep ->
                lastSeenRep.asProduct()
            }
        }
    }

    override fun getLastSeenProductStream(productUrlLink: String): Flow<Product> {
        return dao.getLastSeenProduct(productUrlLink).map {
            it.asProduct()
        }
    }

    override fun getLastSeenProductsStream(offset: Int, limit: Int): Flow<List<Product>> {
        return dao.getLastSeenProducts(offset, limit).map {
            it.map { lastSeenRep ->
                lastSeenRep.asProduct()
            }
        }
    }
}