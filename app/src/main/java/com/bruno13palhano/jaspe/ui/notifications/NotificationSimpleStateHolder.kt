package com.bruno13palhano.jaspe.ui.notifications

import androidx.navigation.NavController
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.NotificationTypes

object NotificationSimpleStateHolder {

    fun navigateTo(navController: NavController, type: String) {
        when (type) {
            NotificationTypes.ANNOUNCEMENT.type -> {
                navController.navigate(R.id.action_to_blog)
            }
            NotificationTypes.NEW_PRODUCTS.type -> {
                navController.navigate(R.id.action_to_product)
            }
            NotificationTypes.OFFERS.type -> {
                navController.navigate(R.id.action_to_offers_category)
            }
            NotificationTypes.DEFAULT.type -> {
                navController.navigate(R.id.action_to_home)
            }
        }
    }
}