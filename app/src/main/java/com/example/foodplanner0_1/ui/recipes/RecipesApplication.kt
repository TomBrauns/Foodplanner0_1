package com.example.foodplanner0_1.ui.recipes

import android.app.Application
import android.util.Log
import com.example.foodplanner0_1.ui.recipes.model.RecipesNavigationRepository
import com.example.foodplanner0_1.ui.recipes.model.FileManager

class RecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("XXX", "XXX")

        // initializing repositories
        val localFileManager = FileManager.initialize(applicationContext)
        RecipesNavigationRepository.initialize(localFileManager)
    }

}

