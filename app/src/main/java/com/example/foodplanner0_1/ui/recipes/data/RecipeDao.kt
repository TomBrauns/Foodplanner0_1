package com.example.foodplanner0_1.ui.recipes.data

import androidx.room.*
import com.example.foodplanner0_1.ui.calender.data.Meal
import com.example.foodplanner0_1.ui.calender.data.MealsName
import com.example.foodplanner0_1.ui.shoppinglist.data.ShoppingItem
import com.example.foodplanner0_1.ui.shoppinglist.data.ShoppingList
import java.util.*


// Database requests are handled here. Direct Requests
@Dao
interface RecipeDao {
    // Function to fetch all Recipes
    @Query("SELECT * FROM recipe")
    fun getRecipes(): List<Recipe>

    //Function to fetch a specific Recipe
    // (ID required as parameter)
    @Query("SELECT * FROM recipe WHERE id=(:id) ORDER BY title ASC")
    suspend fun getRecipe(id: UUID): Recipe

    @Insert
    suspend fun addRecipe(recipe: Recipe)

    @Query("UPDATE recipe SET title = :title, description = :description, effort = :effort, ingredients = :ingredients, steps = :steps WHERE id = :id")
    suspend fun updateRecipe(id : UUID, title : String, description : String, effort : String, ingredients : String, steps : String)

    @Query("DELETE FROM recipe WHERE id=(:id)")
    suspend fun deleteRecipe(id: UUID)
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

    @Query("SELECT * FROM meal WHERE month = :month AND year = :year")
    suspend fun getMonth(month : Int, year : Int) : List<Meal>

    @Query("SELECT item, COUNT(*) as count FROM shoppingItem GROUP BY item")
    suspend fun getShoppingList() : List<ShoppingList>

    @Insert
    suspend fun addShoppingItem(item : ShoppingItem)

    @Query("DELETE FROM shoppingItem WHERE id = (SELECT id FROM shoppingitem WHERE item = :item LIMIT 1)")
    suspend fun deleteOne(item : String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    fun addShoppingCart(items: List<ShoppingItem>)
}