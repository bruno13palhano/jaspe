package com.bruno13palhano.jaspe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = MainViewModelFactory(application).create(MainViewModel::class.java)

        viewModel.fetchDataFromServe()

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
                    if (!item.isChecked) {
                        lifecycleScope.launch {
                            delay(270L)
                            navController.apply {
                                popBackStack(R.id.nav_graph, inclusive = false, saveState = false)
                                navigate(R.id.homeFragment)
                            }
                        }
                    }
                }

                R.id.searchFragment -> {
                    if (!item.isChecked) {
                        lifecycleScope.launch {
                            delay(270L)
                            navController.apply {
                                popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                                navigate(R.id.searchFragment)
                            }
                        }
                    }
                }

                R.id.offersFragment -> {
                    if (!item.isChecked) {
                        lifecycleScope.launch {
                            delay(270L)
                            navController.apply {
                                popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                                navigate(R.id.offersFragment)
                            }
                        }
                    }
                }

                R.id.favoritesFragment -> {
                    if (!item.isChecked) {
                        lifecycleScope.launch {
                            delay(270L)
                            navController.apply {
                                popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                                navigate(R.id.favoritesFragment)
                            }
                        }
                    }
                }

                R.id.categoryFragment -> {
                    if (!item.isChecked) {
                        lifecycleScope.launch {
                            delay(270L)
                            navController.apply {
                                popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                                navigate(R.id.categoryFragment)
                            }
                        }
                    }
                }

                R.id.accountFragment -> {
                    if (!item.isChecked) {
                        lifecycleScope.launch {
                            delay(270L)
                            navController.apply {
                                popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                                navigate(R.id.accountFragment)
                            }
                        }
                    }
                }

                R.id.blogCategoryFragment -> {
                    if (!item.isChecked) {
                        lifecycleScope.launch {
                            delay(270L)
                            navController.apply {
                                popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                                navigate(R.id.blogCategoryFragment)
                            }
                        }
                    }
                }

                R.id.helpFragment -> {
                    if (!item.isChecked) {
                        lifecycleScope.launch {
                            delay(270L)
                            navController.apply {
                                popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                                navigate(R.id.helpFragment)
                            }
                        }
                    }
                }
            }

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