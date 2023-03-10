package com.bruno13palhano.jaspe.ui.category.common

import android.content.Context
import androidx.navigation.NavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.Route

object CategoriesSimpleStateHolder {

    fun navigateToProduct(
        navController: NavController,
        productUrlLink: String,
        productType: String
    ) {
        navController.navigate(CategoriesFragmentDirections
            .actionCategoriesToProduct(productUrlLink, productType))
    }

    fun setToolbarTitle(context: Context, route: String): String {
        return when (route) {
            Route.BABY.route -> {
                context.getString(R.string.baby_category_label)
            }
            Route.MARKET.route -> {
                context.getString(R.string.amazon_category_label)
            }
            Route.AVON.route -> {
                context.getString(R.string.avon_category_label)
            }
            Route.NATURA.route -> {
                context.getString(R.string.natura_category_label)
            }
            Route.OFFERS.route -> {
                context.getString(R.string.offers_category_label)
            }
            Route.LAST_SEEN.route -> {
                context.getString(R.string.last_seen_category_label)
            }
            else -> ""
        }
    }
}