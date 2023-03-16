package com.bruno13palhano.jaspe

import android.content.Context
import android.view.MenuItem
import androidx.navigation.NavController
import com.bruno13palhano.jaspe.ui.common.openEmail
import com.bruno13palhano.jaspe.ui.common.openInstagram
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.ContactInfo
import kotlinx.coroutines.delay

object MainStateHolder {

    suspend fun navigateTo(
        navController: NavController,
        item: MenuItem,
        context: Context,
        contactInfo: ContactInfo
    ) {
        if (!item.isChecked) {
            delay(275L)
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_home)
                    }
                }

                R.id.searchDialogFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_search_dialog)
                    }
                }

                R.id.offersFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_offers_category)
                    }
                }

                R.id.notificationsFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_notifications)
                    }
                }

                R.id.favoritesFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_favorites)
                    }
                }

                R.id.categoryFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_category)
                    }
                }

                R.id.accountFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_account)
                    }
                }

                R.id.blogFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_blog)
                    }
                }

                R.id.helpFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_help)
                    }
                }

                R.id.whatsapp -> {
                    openWhatsApp(context, contactInfo.contactWhatsApp, "")
                }

                R.id.instagram -> {
                    openInstagram(context, contactInfo.contactInstagram)
                }

                R.id.email -> {
                    openEmail(context, contactInfo.contactEmail)
                }
            }
        }
    }
}