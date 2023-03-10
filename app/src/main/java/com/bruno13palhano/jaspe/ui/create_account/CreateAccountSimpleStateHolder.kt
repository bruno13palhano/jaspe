package com.bruno13palhano.jaspe.ui.create_account

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.bruno13palhano.jaspe.DrawerLock

object CreateAccountSimpleStateHolder {

    fun setDrawerEnable(activity: FragmentActivity?) {
        ((activity as DrawerLock)).setDrawerEnable(true)
    }

    fun navigateToHome(navController: NavController) {
        navController.navigate(CreateAccountFragmentDirections
            .actionCreateAccountToHome())
    }
}