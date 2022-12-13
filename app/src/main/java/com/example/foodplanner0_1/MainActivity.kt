package com.example.foodplanner0_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodplanner0_1.databinding.ActivityMainBinding
import com.example.foodplanner0_1.ui.recipes.ui.RecipesListFragment
import com.example.foodplanner0_1.ui.recipes.viewmodel.RecipesNavigationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), RecipesListFragment.Callbacks
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: RecipesNavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(this).get(RecipesNavigationViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_user,           // Implementation of Navbar-Element "User"
            R.id.navigation_calender,       // Implementation of Navbar-Element "Calender"
            R.id.navigation_recipes,        // Implementation of Navbar-Element "Recipes"
            R.id.navigation_shoppingList))  // Implementation of Navbar-Element "shoppinglist"
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onRecipesSelected(recipesId: String) {
        val toast = Toast.makeText(
            getApplication(), "Recipe selected.",
            Toast.LENGTH_SHORT
        )
        toast.show()
    }
}
