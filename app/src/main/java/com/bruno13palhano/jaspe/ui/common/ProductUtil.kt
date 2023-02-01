package com.bruno13palhano.jaspe.ui.common

import androidx.navigation.NavController
import com.bruno13palhano.jaspe.ui.category.amazon.AmazonCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.avon.AvonCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.baby.BabyCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.lastseen.LastSeenCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.natura.NaturaCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.category.offers.OffersCategoryFragmentDirections
import com.bruno13palhano.jaspe.ui.home.HomeFragmentDirections
import com.bruno13palhano.jaspe.ui.search.SearchDialogFragmentDirections
import com.bruno13palhano.model.Product
import com.bruno13palhano.model.Route

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
    value: String
) {
    when (route) {
        Route.BABY.route -> {
            navController.navigate(
                BabyCategoryFragmentDirections.actionBabyCategoryToProduct(value))
        }
        Route.MARKET.route -> {
            navController.navigate(
                AmazonCategoryFragmentDirections.actionMarketCategoryToProduct(value))
        }
        Route.AVON.route -> {
            navController.navigate(
                AvonCategoryFragmentDirections.actionAvonCategoryToProduct(value))
        }
        Route.NATURA.route -> {
            navController.navigate(
                NaturaCategoryFragmentDirections.actionNaturaCategoryToProduct(value))
        }
        Route.OFFERS.route -> {
            navController.navigate(
                OffersCategoryFragmentDirections.actionOffersCategoryToProduct(value))
        }
        Route.LAST_SEEN.route -> {
            navController.navigate(
                LastSeenCategoryFragmentDirections.actionLastSeenCategoryToProduct(value))
        }
        Route.SEARCH_DIALOG.route -> {
            navController.navigate(
                SearchDialogFragmentDirections.actionSearchDialogToSearch(value))
        }
        Route.HOME.route -> {
            navController.navigate(
                HomeFragmentDirections.actionHomeToProduct(value))
        }
    }
}