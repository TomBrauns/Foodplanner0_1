package com.example.foodplanner0_1.ui.recipes

import android.app.Application
import android.util.Log
import com.example.foodplanner0_1.ui.recipes.model.RecipesNavigationRepository
import com.example.foodplanner0_1.ui.recipes.model.FileManager
import com.example.foodplanner0_1.ui.recipes.model.RecipeRepository

class RecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // initializing repositories
        // next two lines obsolete
        val localFileManager = FileManager.initialize(applicationContext)
        RecipesNavigationRepository.initialize(localFileManager)
        RecipeRepository.initialize(this)
    }

}

