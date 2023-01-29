package com.example.foodplanner0_1.ui.recipes

import android.app.Application
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.example.foodplanner0_1.ui.recipes.model.RecipeRepository

class RecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        RecipeRepository.initialize(this)
    }

}

