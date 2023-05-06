package com.sergei.apprecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.fragmentContainerView)
                    as NavHostFragment

        navController =
            navHostFragment.navController
        Log.d(TAG, "NavController set")

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        Log.d(TAG, "Bottom navigation created")

        //TODO: Fix hiding navigation bar on fullscreen fragments
/*        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.search_local, R.id.search_online, R.id.settings ->
                    bottomNavigationView.visibility = View.VISIBLE

                else -> bottomNavigationView.visibility = View.GONE
            }
        }*/

        bottomNavigationView.setupWithNavController(navController)

    }
}