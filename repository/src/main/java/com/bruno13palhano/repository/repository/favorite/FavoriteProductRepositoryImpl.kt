package com.bruno13palhano.repository.repository.favorite

import com.bruno13palhano.model.FavoriteProduct
import com.bruno13palhano.repository.database.dao.FavoriteProductDao
import com.bruno13palhano.repository.model.asFavoriteProduct
import com.bruno13palhano.repository.model.asFavoriteProductRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FavoriteProductRepositoryImpl @Inject constructor(
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

    override fun getFavoriteByLinkStream(favoriteProductLink: String): Flow<FavoriteProduct> {
        return dao.getFavoriteByLink(favoriteProductLink).map {
            it.asFavoriteProduct()
        }
    }

    override fun getAllFavoriteProductsStream(): Flow<List<FavoriteProduct>> {
        return dao.getAllFavorites().map {
            it.map { favorite ->
                favorite.asFavoriteProduct()
            }
        }
    }

    override fun getAllFavoriteProductsVisibleStream(): Flow<List<FavoriteProduct>> {
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