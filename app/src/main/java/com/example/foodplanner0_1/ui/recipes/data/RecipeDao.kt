package com.example.foodplanner0_1.ui.recipes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodplanner0_1.ui.calender.data.Meal
import com.example.foodplanner0_1.ui.calender.data.MealsName
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

    //Function to fetch a specific Meal Plan
    // (ID required as parameter)
    @Query("SELECT *, " +
            "(SELECT title FROM recipe WHERE id = breakfast) as breakfastName, " +
            "(SELECT title FROM recipe WHERE id = lunch) as lunchName, " +
            "(SELECT title FROM recipe WHERE id = dinner) as dinnerName " +
            "FROM meal WHERE day = :day AND month = :month AND year = :year")
    suspend fun getMeal(day : Int, month : Int, year : Int): MealsName?

    @Insert
    suspend fun addMeal(meal: Meal)

    @Query("UPDATE meal SET breakfast = :breakfast, lunch = :lunch, dinner = :dinner WHERE id = :uuid")
    suspend fun updateMeal(uuid : UUID, breakfast : UUID?, lunch : UUID?, dinner : UUID?)
}