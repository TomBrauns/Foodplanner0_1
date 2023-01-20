package com.example.foodplanner0_1.ui.recipes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID

// Database requests are handled here. Direct Requests
@Dao
interface RecipeDao {
    // Function to fetch all Recipes
    @Query("SELECT * FROM recipe")
    fun getRecipes(): Flow<List<Recipe>>

    //Function to fetch a specific Recipe
    // (ID required as parameter)
    @Query("SELECT * FROM recipe WHERE id=(:id)")
    suspend fun getRecipe(id: UUID): Recipe

    @Insert
    suspend fun addRecipe(recipe: Recipe)
}