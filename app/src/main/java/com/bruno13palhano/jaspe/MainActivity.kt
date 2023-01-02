package com.bruno13palhano.jaspe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        val builder = AppBarConfiguration.Builder(navController.graph)
        builder.setOpenableLayout(drawer)
        val appBarConfiguration = builder.build()

        toolbar.setupWithNavController(navController, appBarConfiguration)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (item.isChecked) {
                        drawer.closeDrawer(GravityCompat.START)
                    } else {
                        navController.apply {
                            popBackStack(R.id.nav_graph, inclusive = false, saveState = true)
                            navigate(R.id.homeFragment)
                        }
                    }
                }

                R.id.searchFragment -> {
                    if (item.isChecked) {
                        drawer.closeDrawer(GravityCompat.START)
                    } else {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.searchFragment)
                        }
                    }
                }

                R.id.offersFragment -> {
                    if (item.isChecked) {
                        drawer.closeDrawer(GravityCompat.START)
                    } else {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.offersFragment)
                        }
                    }
                }

                R.id.favoritesFragment -> {
                    if (item.isChecked) {
                        drawer.closeDrawer(GravityCompat.START)
                    } else {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.favoritesFragment)
                        }
                    }
                }

                R.id.accountFragment -> {
                    if (item.isChecked) {
                        drawer.closeDrawer(GravityCompat.START)
                    } else {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.accountFragment)
                        }
                    }
                }

                R.id.helpFragment -> {
                    if (item.isChecked) {
                        drawer.closeDrawer(GravityCompat.START)
                    } else {
                        navController.apply {
                            popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                            navigate(R.id.helpFragment)
                        }
                    }
                }
            }

            drawer.closeDrawer(GravityCompat.START)
            true
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