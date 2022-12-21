package com.example.foodplanner0_1.ui.recipes.data

import androidx.room.Database
import androidx.room.RoomDatabase

// Entities are from the type Recipe
@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase(){
    abstract fun recipeDao() : RecipeDao
}
