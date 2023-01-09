package com.bruno13palhano.repository

import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.dao.FavoriteProductDao
import com.bruno13palhano.repository.util.convertFavoriteRepToFavorite
import com.bruno13palhano.repository.util.convertFavoriteToFavoriteRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class FavoriteProductRepositoryImpl(
    private val dao: FavoriteProductDao
) : FavoriteProductRepository {
    override suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct) {
        dao.insert(convertFavoriteToFavoriteRep(favoriteProduct))
    }

    override suspend fun updateFavoriteProduct(favoriteProduct: FavoriteProduct) {
        dao.update(convertFavoriteToFavoriteRep(favoriteProduct))
    }

    override suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteProduct) {
        dao.delete(convertFavoriteToFavoriteRep(favoriteProduct))
    }

    override suspend fun deleteFavoriteProductById(favoriteProductId: Long) {
        dao.deleteFavoriteProductById(favoriteProductId)
    }

    override fun getFavoriteProduct(favoriteProductId: Long): Flow<FavoriteProduct> {
        return dao.getFavorite(favoriteProductId).map {
            convertFavoriteRepToFavorite(it)
        }
    }

    override fun getAllFavoriteProducts(): Flow<List<FavoriteProduct>> {
        return dao.getAllFavorites().map {
            it.map { favorite ->
                convertFavoriteRepToFavorite(favorite)
            }
        }
    }
}