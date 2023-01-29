package com.example.foodplanner0_1.ui.recipes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.UUID

// Database requests are handled here. Direct Requests
@Dao
interface RecipeDao {
    // Function to fetch all Recipes
    @Query("SELECT * FROM recipe")
    fun getRecipes(): List<Recipe>

    //Function to fetch a specific Recipe
    // (ID required as parameter)
    @Query("SELECT * FROM recipe WHERE id=(:id)")
    suspend fun getRecipe(id: UUID): Recipe

    @Insert
    suspend fun addRecipe(recipe: Recipe)

    @Query("UPDATE recipe SET title = :title, description = :description, effort = :effort, ingredients = :ingredients, steps = :steps WHERE id = :id")
    suspend fun updateRecipe(id : UUID, title : String, description : String, effort : String, ingredients : String, steps : String)
}