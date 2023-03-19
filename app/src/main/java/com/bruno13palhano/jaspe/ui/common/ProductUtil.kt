package com.bruno13palhano.jaspe.ui.common

import androidx.navigation.NavController
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
    route: Route,
    firstArg: String,
    secondArg: String,
) {
    when (route) {
        Route.SEARCH_DIALOG -> {
            navController.navigate(
                SearchDialogFragmentDirections.actionSearchDialogToSearch(firstArg))
        }
        Route.SEARCH -> {
            navController.navigate(
                SearchFragmentDirections.actionSearchToProduct(firstArg, secondArg))
        }
        Route.FAVORITE -> {
            navController.navigate(
                FavoritesFragmentDirections.actionFavoriteToProduct(firstArg, secondArg))
        }
        Route.HOME -> {
            navController.navigate(
                HomeFragmentDirections.actionHomeToProduct(firstArg, secondArg))
        }
        else -> {}
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