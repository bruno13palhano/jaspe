package com.bruno13palhano.jaspe.ui.home

import androidx.navigation.NavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.Route

object HomeSimpleStateHolder {

    fun navigateToProduct(
        navController: NavController,
        productUrlLink: String,
        productType: String
    ) {
        navController.navigate(HomeFragmentDirections
            .actionHomeToProduct(productUrlLink, productType))
    }

    fun navigateTo(navController: NavController, route: String) {
        when (route) {
            Route.SEARCH_DIALOG.route -> {
                navController.apply {
                    popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                    navigate(R.id.action_to_search_dialog)
                }
            }
            Route.OFFERS.route -> {
                navController.apply {
                    popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                    navigate(R.id.action_to_offers_category)
                }
            }
            else -> {
                navController.navigate(HomeFragmentDirections
                    .actionHomeToCommonCategories(route))
            }
        }
    }
}