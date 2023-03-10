package com.bruno13palhano.jaspe.ui.login

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.bruno13palhano.jaspe.DrawerLock

object LoginSimpleStateHolder {

    fun onCloseOrSuccess(
        navController: NavController,
        activity: FragmentActivity,
        enabled: Boolean
    ) {
        setDrawerEnable(activity, enabled)
        navigateToHome(navController)
    }

    fun setDrawerEnable(
        activity: FragmentActivity,
        enabled: Boolean
    ) {
        ((activity as DrawerLock)).setDrawerEnable(enabled)
    }

    private fun navigateToHome(navController: NavController) {
        navController.navigate(LoginFragmentDirections.actionLoginToHome())
    }

    fun navigateToCreateAccount(navController: NavController) {
        navController.navigate(LoginFragmentDirections.actionLoginToCreateAccount())
    }
}