package com.bruno13palhano.repository.repository.usercase

import com.bruno13palhano.common.di.ApplicationScope
import com.bruno13palhano.model.Company
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Type
import com.bruno13palhano.repository.database.dao.ProductDao
import com.bruno13palhano.repository.model.asLastSeenProduct
import com.bruno13palhano.repository.model.asProduct
import com.bruno13palhano.repository.model.asProductRep
import com.bruno13palhano.repository.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    @ApplicationScope private val scoped: CoroutineScope
) : ProductRepository {
    override suspend fun insertProducts(productList: List<Product>) {
        dao.insertAll(productList.map {
             it.asProductRep()
        })
    }

    override val allProducts: Flow<List<Product>> =
        dao.getAll().map {
            it.map { productRep ->
                productRep.asProduct()
            }
        }.stateIn(
            initialValue = emptyList(),
            scope = scoped,
            started = WhileSubscribed(5000)
        )

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

    override val amazonProducts: Flow<List<Product>> =
        dao.getByCompany(Company.AMAZON.company, 0, 20)
            .map {
                it.map { productRep ->
                    productRep.asProduct()
                }
            }
            .stateIn(
                initialValue = emptyList(),
                scope = scoped,
                started = WhileSubscribed(5000)
            )

    override val avonProducts: Flow<List<Product>> =
        dao.getByCompany(Company.AVON.company, 0, 20)
            .map {
                it.map { productRep ->
                    productRep.asProduct()
                }
            }
            .stateIn(
                initialValue = emptyList(),
                scope = scoped,
                started = WhileSubscribed(5000)
            )

    override val naturaProducts: Flow<List<Product>> =
        dao.getByCompany(Company.NATURA.company, 0, 20)
            .map {
                it.map { productRep ->
                    productRep.asProduct()
                }
            }
            .stateIn(
                initialValue = emptyList(),
                scope = scoped,
                started = WhileSubscribed(5000)
            )


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

    override val lastSeenProducts: Flow<List<Product>> =
        dao.getLastSeenProducts(0, 20).map {
                it.map { lastSeenRep ->
                    lastSeenRep.asProduct()
                }
            }
            .stateIn(
                initialValue = emptyList(),
                scope = scoped,
                started = WhileSubscribed(5000)
            )

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