package com.bruno13palhano.jaspe.ui.account

import androidx.navigation.NavController

object AccountSimpleStateHolder {

    fun navigateToLogin(navController: NavController) {
        navController.navigate(AccountFragmentDirections.actionAccountToLogin())
    }

    fun navigateToHome(navController: NavController) {
        navController.navigate(AccountFragmentDirections.actionToHome())
    }
}