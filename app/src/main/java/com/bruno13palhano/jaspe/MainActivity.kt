package com.bruno13palhano.jaspe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.bruno13palhano.model.ContactInfo
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DrawerLock {
    private lateinit var contactInfo: ContactInfo
    private lateinit var drawer: DrawerLayout
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

            lifecycleScope.launch {
                MainStateHolder.navigateTo(
                    navController = navController,
                    item = item,
                    context = this@MainActivity,
                    contactInfo = contactInfo
                )
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