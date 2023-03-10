package com.bruno13palhano.jaspe.ui.category.offers

import androidx.navigation.NavController

object OffersSimpleStateHolder {

    fun navigateToProduct(
        navController: NavController,
        productUrlLink: String,
        productType: String
    ) {
        navController.navigate(OffersFragmentDirections
            .actionOffersToProduct(productUrlLink, productType))
    }
}