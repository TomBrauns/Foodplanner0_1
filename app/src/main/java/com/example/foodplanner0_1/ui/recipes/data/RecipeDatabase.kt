package com.example.foodplanner0_1.ui.recipes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodplanner0_1.ui.calender.data.Meal
import com.example.foodplanner0_1.ui.shoppinglist.data.ShoppingItem

// Entities are from the type Recipe
private const val DATABASE_NAME = "recipe-database"

@Database(entities = [Recipe::class, Meal::class, ShoppingItem::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var instance: RecipeDatabase? = null
        fun initialize(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, RecipeDatabase::class.java, DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(RecipePreset(context))
                    .build()

            }
        }

        fun get(): RecipeDatabase {
            return instance
                ?: throw IllegalStateException("RecipeDatabase must be initialized")
        }
    }

}
