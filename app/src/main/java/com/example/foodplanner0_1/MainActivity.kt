package com.example.foodplanner0_1

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodplanner0_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_user,           // Implementation of Navbar-Element "User"
            R.id.navigation_calender,       // Implementation of Navbar-Element "Calender"
            R.id.navigation_addrecipe,      // Implementation of Navbar-Element "Add", although this is surely not a Fragment in the regular way
            R.id.navigation_recipes,        // Implementation of Navbar-Element "Recipes"
            R.id.navigation_shoppingList))  // Implementation of Navbar-Element "shoppinglist"
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}