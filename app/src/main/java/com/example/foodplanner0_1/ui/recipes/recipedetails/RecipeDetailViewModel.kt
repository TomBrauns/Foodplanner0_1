package com.example.foodplanner0_1.ui.recipes.recipedetails

import androidx.lifecycle.ViewModel
import java.util.UUID
import androidx.lifecycle.ViewModelProvider
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.model.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class RecipeDetailViewModel(recipesId: UUID) : ViewModel() {}
/*
    private val recipesRepository = RecipeRepository.get()

    private val _recipes: MutableStateFlow<Recipe?> = MutableStateFlow(null)
    val recipes: StateFlow<Recipe?> = _recipes.asStateFlow()
    init {
        viewModelScope.launch {
            _recipes.value = RecipeRepository.getRecipes(recipesId)
        }
    }

    fun updateRecipes(onUpdate: (RecipesListItem) -> RecipesListItem) {
        _recipes.update { oldRecipes ->
            oldRecipes?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        recipes.value?.let { recipesRepository.updateRecipes(it) }
    }
}

class RecipesDetailViewModelFactory(
    private val recipesId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeDetailViewModel(recipesId) as T
*/