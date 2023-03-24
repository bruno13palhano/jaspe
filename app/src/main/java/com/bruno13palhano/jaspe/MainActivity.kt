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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bruno13palhano.jaspe.ui.common.openEmail
import com.bruno13palhano.jaspe.ui.common.openInstagram
import com.bruno13palhano.jaspe.ui.common.openWhatsApp
import com.bruno13palhano.jaspe.work.*
import com.bruno13palhano.model.ContactInfo
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DrawerLock {
    private lateinit var contactInfo: ContactInfo
    private lateinit var drawer: DrawerLayout
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workManager = WorkManager.getInstance(application)
        val fetchProductData =
            PeriodicWorkRequestBuilder<ProductWork>(1, TimeUnit.HOURS)
                .build()
        val fetchBannerData =
            PeriodicWorkRequestBuilder<BannerWork>(1, TimeUnit.HOURS)
                .build()

        val fetchContactData =
            PeriodicWorkRequestBuilder<ContactWork>(1, TimeUnit.HOURS)
                .build()

        val fetchNotificationData =
            PeriodicWorkRequestBuilder<NotificationWork>(15, TimeUnit.MINUTES)
                .build()

        initWorks(
            workManager = workManager,
            productPeriodicWorkRequest = fetchProductData,
            bannerPeriodicWorkRequest = fetchBannerData,
            contactPeriodicWorkRequest = fetchContactData,
            notificationPeriodicWorkRequest = fetchNotificationData
        )

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
                navigateTo(
                    navController = navController,
                    item = item,
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

    private suspend fun navigateTo(
        navController: NavController,
        item: MenuItem,
        contactInfo: ContactInfo
    ) {
        if (!item.isChecked) {
            delay(275L)
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = true, saveState = true)
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
                        navigate(R.id.action_to_blog_category)
                    }
                }

                R.id.helpFragment -> {
                    navController.apply {
                        popBackStack(R.id.homeFragment, inclusive = false, saveState = true)
                        navigate(R.id.action_to_help)
                    }
                }

                R.id.whatsapp -> {
                    openWhatsApp(this@MainActivity, contactInfo.contactWhatsApp, "")
                }

                R.id.instagram -> {
                    openInstagram(this@MainActivity, contactInfo.contactInstagram)
                }

                R.id.email -> {
                    openEmail(this@MainActivity, contactInfo.contactEmail)
                }
            }
        }
    }

    private fun initWorks(
        workManager: WorkManager,
        productPeriodicWorkRequest: PeriodicWorkRequest,
        bannerPeriodicWorkRequest: PeriodicWorkRequest,
        contactPeriodicWorkRequest: PeriodicWorkRequest,
        notificationPeriodicWorkRequest: PeriodicWorkRequest
    ) {
        setWork(WorkNames.PRODUCT, workManager, productPeriodicWorkRequest)
        setWork(WorkNames.BANNER, workManager, bannerPeriodicWorkRequest)
        setWork(WorkNames.CONTACT, workManager, contactPeriodicWorkRequest)
        setWork(WorkNames.NOTIFICATION, workManager, notificationPeriodicWorkRequest)
    }

    private fun setWork(
        workName: WorkNames,
        workManager: WorkManager,
        periodicWorkRequest: PeriodicWorkRequest
    ) {
        workManager.enqueueUniquePeriodicWork(
            workName.tag, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)
    }
}

interface DrawerLock {
    fun setDrawerEnable(enabled: Boolean)
}