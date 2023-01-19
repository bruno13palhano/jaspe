package com.bruno13palhano.repository

import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.dao.FavoriteProductDao
import com.bruno13palhano.repository.model.asFavoriteProduct
import com.bruno13palhano.repository.model.asFavoriteProductRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class FavoriteProductRepositoryImpl(
    private val dao: FavoriteProductDao
) : FavoriteProductRepository {
    override suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct) {
        dao.insert(favoriteProduct.asFavoriteProductRep())
    }

    override suspend fun updateFavoriteProduct(favoriteProduct: FavoriteProduct) {
        dao.update(favoriteProduct.asFavoriteProductRep())
    }

    override suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteProduct) {
        dao.delete(favoriteProduct.asFavoriteProductRep())
    }

    override suspend fun deleteAllFavoriteProduct(favoriteProductList: List<FavoriteProduct>) {
        dao.deleteAll(favoriteProductList.map {
            it.asFavoriteProductRep()
        })
    }

    override suspend fun deleteFavoriteProductById(favoriteProductId: Long) {
        dao.deleteFavoriteProductById(favoriteProductId)
    }

    override fun getFavoriteProduct(favoriteProductId: Long): Flow<FavoriteProduct> {
        return dao.getFavorite(favoriteProductId).map {
            it.asFavoriteProduct()
        }
    }

    override fun getAllFavoriteProducts(): Flow<List<FavoriteProduct>> {
        return dao.getAllFavorites().map {
            it.map { favorite ->
                favorite.asFavoriteProduct()
            }
        }
    }
}