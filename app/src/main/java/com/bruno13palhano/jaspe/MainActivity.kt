package com.bruno13palhano.jaspe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.bruno13palhano.jaspe.ui.common.openEmail
import com.bruno13palhano.jaspe.ui.common.openInstagram
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.repository.RepositoryFactory
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = MainViewModelFactory(application, RepositoryFactory(this)
            .createContactInfoRepository()).create(MainViewModel::class.java)

        var contactInfo = ContactInfo()

        lifecycle.coroutineScope.launch {
            viewModel.contactInfo.collect {
                contactInfo = it
            }
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        val builder = AppBarConfiguration.Builder(navController.graph)
        builder.setOpenableLayout(drawer)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)

        navView.setNavigationItemSelectedListener { item ->
            drawer.closeDrawer(GravityCompat.START)

            when (item.itemId) {
                R.id.homeFragment -> {
                    navigateTo(navController, item, R.id.homeFragment)
                }

                R.id.searchDialogFragment -> {
                    navigateTo(navController, item, R.id.searchDialogFragment)
                }

                R.id.offersCategoryFragment -> {
                    navigateTo(navController, item, R.id.offersCategoryFragment)
                }

                R.id.favoritesFragment -> {
                    navigateTo(navController, item, R.id.favoritesFragment)
                }

                R.id.categoryFragment -> {
                    navigateTo(navController, item, R.id.categoryFragment)
                }

                R.id.accountFragment -> {
                    navigateTo(navController, item, R.id.accountFragment)
                }

                R.id.blogCategoryFragment -> {
                    navigateTo(navController, item, R.id.blogCategoryFragment)
                }

                R.id.helpFragment -> {
                    navigateTo(navController, item, R.id.helpFragment)
                }

                R.id.whatsapp -> {
                    openWhatsApp(this, contactInfo.contactWhatsApp)
                }

                R.id.instagram -> {
                    openInstagram(this, contactInfo.contactInstagram)
                }

                R.id.email -> {
                    openEmail(this, contactInfo.contactEmail)
                }
            }

            true
        }
    }

    private fun navigateTo(
        navController: NavController,
        item: MenuItem,
        routeId: Int
    ) {
        if (!item.isChecked) {
            if (routeId == R.id.homeFragment) {
                lifecycleScope.launch {
                    delay(270L)
                    navController.apply {
                        popBackStack(R.id.nav_graph, inclusive = false, saveState = false)
                        navigate(routeId)
                    }
                }
            } else {
                lifecycleScope.launch {
                    delay(270L)
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(routeId)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)
    }
}