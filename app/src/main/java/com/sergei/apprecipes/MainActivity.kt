package com.sergei.apprecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.fragmentContainerView)
                    as NavHostFragment

        navController =  navHostFragment.navController //findNavController(R.id.fragmentContainerView)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search_local -> {
                    navController.navigate(R.id.searchLocalFragment)
                    true
                }
                R.id.search_online -> {
                    navController.navigate(R.id.searchOnlineFragment)
                    true
                }
                R.id.settings -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.search_local, R.id.search_online, R.id.settings
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        bottomNavigationView.setupWithNavController(navController)

    }
}