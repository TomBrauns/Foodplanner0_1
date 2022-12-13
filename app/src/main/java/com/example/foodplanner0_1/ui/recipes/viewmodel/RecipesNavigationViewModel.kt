package com.example.foodplanner0_1.ui.recipes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foodplanner0_1.ui.recipes.model.RecipesNavigationRepository
import com.example.foodplanner0_1.ui.recipes.data.RecipesListItem

class RecipesNavigationViewModel(application: Application) : AndroidViewModel(application) {

    private val recipesNavigationRepository = RecipesNavigationRepository.get()

    fun getMyRecipes(): List<RecipesListItem> {
        return recipesNavigationRepository.recipesCatalog

    }

}

