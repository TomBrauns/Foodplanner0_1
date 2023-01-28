package com.example.foodplanner0_1.ui.recipes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Entities are from the type Recipe
private const val DATABASE_NAME = "recipe-database"

@Database(entities = [Recipe::class], version = 4)
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
                    ./** addCallback(
                    RecipePreset(context)
                ).fallbackToDestructiveMigration() .*/build()
            }
        }
        fun get(): RecipeDatabase {
            return instance
                ?: throw IllegalStateException("RecipeDatabase must be initialized")
        }
    }

}
