package com.example.foodplanner0_1.ui.recipes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.model.RecipesNavigationRepository
import com.example.foodplanner0_1.ui.recipes.data.RecipesListItem
import com.example.foodplanner0_1.ui.recipes.model.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipesNavigationViewModel(application: Application) : AndroidViewModel(application) {

    private val recipesNavigationRepository = RecipesNavigationRepository.get()
    private val recipeRepository = RecipeRepository.get()

    fun getMyRecipes(): List<RecipesListItem> {
        return recipesNavigationRepository.recipesCatalog

    }
    fun getRecipes(): Flow<List<Recipe>>{
        return recipeRepository.getRecipes()
    }
}

