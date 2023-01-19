package com.example.foodplanner0_1.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class RecipesDetailViewModel(recipeId: UUID) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This will be a Detailed Recipe" //Actual input on the tab
    }
    val text: LiveData<String> = _text


}
/*class RecipesDetailViewModel(recipeId: UUID) : ViewModel() {}

    private val recipesRepository = RecipesNavigationRepository.get()

    private val _recipes: MutableStateFlow<RecipesListItem?> = MutableStateFlow(null)
    val recipes: StateFlow<RecipesListItem?> = _recipes.asStateFlow()

    init {
        viewModelScope.launch {
            _recipes.value = RecipesNavigationRepository.getRecipes(recipesId)
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
        return RecipesDetailViewModel(recipesId) as T
    }
}
*/
