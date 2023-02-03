package com.bruno13palhano.jaspe.ui.common

import androidx.navigation.NavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.category.amazon.AmazonCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.avon.AvonCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.baby.BabyCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.lastseen.LastSeenCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.natura.NaturaCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.offers.OffersCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.favorite.FavoritesFragmentDirections
import com.bruno13palhano.jaspe.ui.home.HomeFragmentDirections
import com.bruno13palhano.jaspe.ui.search.FilterType
import com.bruno13palhano.jaspe.ui.search.SearchDialogFragmentDirections
import com.bruno13palhano.jaspe.ui.search.SearchFragmentDirections
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Route
import kotlinx.coroutines.flow.MutableStateFlow

fun prepareLastSeenProduct(product: Product): Product {
    return Product(
        productId = 0L,
        productName = product.productName,
        productUrlImage = product.productUrlImage,
        productPrice = product.productPrice,
        productCompany = product.productCompany,
        productUrlLink = product.productUrlLink,
        productDescription = product.productDescription,
        productType = product.productType
    )
}

fun navigateToProduct(
    navController: NavController,
    route: String,
    firstArg: String,
    secondArg: String,
) {
    when (route) {
        Route.BABY.route -> {
            navController.navigate(
                BabyCategoryFragmentDirections.actionBabyCategoryToProduct(firstArg, secondArg))
        }
        Route.MARKET.route -> {
            navController.navigate(
                AmazonCategoryFragmentDirections.actionMarketCategoryToProduct(firstArg, secondArg))
        }
        Route.AVON.route -> {
            navController.navigate(
                AvonCategoryFragmentDirections.actionAvonCategoryToProduct(firstArg, secondArg))
        }
        Route.NATURA.route -> {
            navController.navigate(
                NaturaCategoryFragmentDirections.actionNaturaCategoryToProduct(firstArg, secondArg))
        }
        Route.OFFERS.route -> {
            navController.navigate(
                OffersCategoryFragmentDirections.actionOffersCategoryToProduct(firstArg, secondArg))
        }
        Route.LAST_SEEN.route -> {
            navController.navigate(
                LastSeenCategoryFragmentDirections.actionLastSeenCategoryToProduct(firstArg, secondArg))
        }
        Route.SEARCH_DIALOG.route -> {
            navController.navigate(
                SearchDialogFragmentDirections.actionSearchDialogToSearch(firstArg))
        }
        Route.SEARCH.route -> {
            navController.navigate(
                SearchFragmentDirections.actionSearchToProduct(firstArg, secondArg))
        }
        Route.FAVORITE.route -> {
            navController.navigate(
                FavoritesFragmentDirections.actionFavoriteToProduct(firstArg, secondArg))
        }
        Route.HOME.route -> {
            navController.navigate(
                HomeFragmentDirections.actionHomeToProduct(firstArg, secondArg))
        }
    }
}

fun getOrderedProducts(products: MutableStateFlow<List<Product>>, filter: FilterType) {
    when (filter) {
        FilterType.DEFAULT -> {
            products.value = products.value.sortedBy { it.productId }
        }
        FilterType.NAME -> {
            products.value = products.value.sortedBy { it.productName }
        }
        FilterType.LOW_PRICE -> {
            products.value = products.value.sortedBy { it.productPrice }
        }
        FilterType.HIGH_PRICE -> {
            products.value = products.value.sortedByDescending { it.productPrice }
        }
    }
}