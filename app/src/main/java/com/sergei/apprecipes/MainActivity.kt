package com.sergei.apprecipes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Setting up Navigation
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.fragmentContainerView)
                    as NavHostFragment

        navController =
            navHostFragment.navController
        Log.d(TAG, "NavController set")

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        Log.d(TAG, "Bottom navigation created")

        // Making bottom navigation invisible on fullscreen fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchLocalFragment, R.id.searchOnlineFragment, R.id.settingsFragment ->
                    bottomNavigationView.visibility = View.VISIBLE

                else -> bottomNavigationView.visibility = View.GONE
            }
        }

        // Setting up the navigation bar
        bottomNavigationView.setupWithNavController(navController)
    }
}