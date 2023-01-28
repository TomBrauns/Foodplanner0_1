package com.example.foodplanner0_1.ui.recipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.model.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val recipeRepository = RecipeRepository.get()

    private val _recipes: MutableStateFlow<List<Recipe>> = MutableStateFlow(emptyList())
    val recipes: StateFlow<List<Recipe>>
        get() = _recipes.asStateFlow()

    init {
//        viewModelScope.launch {
//            recipeRepository.getRecipes().collect {
//                _recipes.value = it
//            }
//        }
    }

    suspend fun addRecipe(recipe: Recipe) {
        recipeRepository.addRecipe(recipe)
    }
}