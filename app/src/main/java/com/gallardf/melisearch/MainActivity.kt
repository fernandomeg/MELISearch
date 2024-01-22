package com.gallardf.melisearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        Thread.sleep(2500)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.view_toolbar)
        val navController = findNavController(R.id.nav_host_main)

        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.title = null
        }

        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener{_,destination, arguments ->
            toolbar.visibility = when (destination.id) {
                R.id.main_fragment -> View.GONE
                else-> View.VISIBLE
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}