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

    override suspend fun deleteAllFavoriteProduct(favoriteProductList: List<FavoriteProduct>) {
        dao.deleteAll(favoriteProductList.map {
            it.asFavoriteProductRep()
        })
    }

    override suspend fun deleteFavoriteProductByUrlLink(favoriteProductUrlLink: String) {
        dao.deleteFavoriteProductByUrlLink(favoriteProductUrlLink)
    }

    override fun getFavoriteByLink(favoriteProductLink: String): Flow<FavoriteProduct> {
        return dao.getFavoriteByLink(favoriteProductLink).map {
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

    override fun getAllFavoriteProductsVisible(): Flow<List<FavoriteProduct>> {
        return dao.getAllFavoritesVisible().map {
            it.map { favorite ->
                favorite.asFavoriteProduct()
            }
        }
    }

    override suspend fun setFavoriteProductVisibilityByUrl(
        favoriteProductUrlLink: String,
        favoriteProductIsVisible: Boolean
    ) {
        dao.setFavoriteProductVisibilityByUrl(favoriteProductUrlLink, favoriteProductIsVisible)
    }
}