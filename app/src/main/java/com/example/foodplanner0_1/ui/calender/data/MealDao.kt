package com.example.foodplanner0_1.ui.calender.data

import com.example.foodplanner0_1.ui.recipes.data.Recipe


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

// Database requests are handled here. Direct Requests
@Dao
interface MealDao {
    //Function to fetch a specific Meal Plan
    // (ID required as parameter)
    @Query("SELECT *, 'x' as breakfastName, 'y' as lunchName, 'z' as dinnerName FROM meal WHERE day = :day AND month = :month AND year = :year")
    suspend fun getMeal(day : Int, month : Int, year : Int): MealsName?

    @Insert
    suspend fun addMeal(meal: Meal)

    @Query("UPDATE meal SET breakfast = :breakfast, lunch = :lunch, dinner = :dinner WHERE id = :uuid")
    suspend fun updateMeal(uuid : UUID, breakfast : UUID?, lunch : UUID?, dinner : UUID?)

}