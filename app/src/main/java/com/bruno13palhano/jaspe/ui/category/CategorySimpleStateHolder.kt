package com.bruno13palhano.jaspe.ui.category

import androidx.navigation.NavController
import com.bruno13palhano.model.Route

object CategorySimpleStateHolder {

    fun navigateToCommonCategories(navController: NavController, route: String) {
        when (route) {
            Route.OFFERS.route -> {
                navController.navigate(CategoryFragmentDirections
                    .actionCategoryToOffers())
            }
            else -> {
                navController.navigate(CategoryFragmentDirections
                    .actionCategoryToCommonCategories(route))
            }
        }
    }
}