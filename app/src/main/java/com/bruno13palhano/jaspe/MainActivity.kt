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
import com.bruno13palhano.jaspe.dependencies.DependenciesApplication
import com.bruno13palhano.jaspe.ui.common.openEmail
import com.bruno13palhano.jaspe.ui.common.openInstagram
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.model.ContactInfo
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), DrawerLock {
    private lateinit var contactInfo: ContactInfo
    private lateinit var drawer: DrawerLayout
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dependenciesContainer = (application as DependenciesApplication).dependenciesContainer
        viewModel = dependenciesContainer.mainViewModelFactory.create()
        contactInfo = ContactInfo()

        lifecycle.coroutineScope.launch {
            viewModel.contactInfo.collect {
                contactInfo = it
            }
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        drawer = findViewById(R.id.drawer_layout)

        val builder = AppBarConfiguration.Builder(navController.graph)
        builder.setOpenableLayout(drawer)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)

        navView.setNavigationItemSelectedListener { item ->
            drawer.closeDrawer(GravityCompat.START)

            navigateTo(
                navController = navController,
                item = item
            )

            true
        }
    }

    private fun navigateTo(
        navController: NavController,
        item: MenuItem
    ) {
        if (!item.isChecked) {
            when (item.itemId) {
                R.id.homeFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_home)
                        }
                    }
                }

                R.id.searchDialogFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_search_dialog)
                        }
                    }
                }

                R.id.offersFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_offers_category)
                        }
                    }
                }

                R.id.notificationsFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_notifications)
                        }
                    }
                }

                R.id.favoritesFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_favorites)
                        }
                    }
                }

                R.id.categoryFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_category)
                        }
                    }
                }

                R.id.accountFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_account)
                        }
                    }
                }

                R.id.blogFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_blog)
                        }
                    }
                }

                R.id.helpFragment -> {
                    lifecycleScope.launch {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.action_to_help)
                        }
                    }
                }

                R.id.whatsapp -> {
                    openWhatsApp(this, contactInfo.contactWhatsApp, "")
                }

                R.id.instagram -> {
                    openInstagram(this, contactInfo.contactInstagram)
                }

                R.id.email -> {
                    openEmail(this, contactInfo.contactEmail)
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

    override fun setDrawerEnable(enabled: Boolean) {
        val lockMode = when (enabled) {
            true -> {
                DrawerLayout.LOCK_MODE_UNLOCKED
            }
            else -> {
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            }
        }

        drawer.setDrawerLockMode(lockMode)
    }
}

interface DrawerLock {
    fun setDrawerEnable(enabled: Boolean)
}